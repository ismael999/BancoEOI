package es.eoi.mundobancario.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportsCuentaListDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.exceptions.PrestamoActivoException;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.repository.PrestamoRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	private PrestamoRepository repository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailManager email;

	private final String DEST = "./src/main/resources/EOI_BANK_PRESTAMO_";

	@Override
	public List<PrestamoDto> findByCuenta(CuentaDto dto) {
		Cuenta cuenta = modelMapper.map(dto, Cuenta.class);
		return repository.findByCuenta(cuenta).stream().map(p -> modelMapper.map(p, PrestamoDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> getPrestamosVivos(CuentaDto cuenta) {
		return repository.findByCuentaAndPagado(modelMapper.map(cuenta, Cuenta.class), false).stream()
				.map(p -> modelMapper.map(p, PrestamoDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PrestamoDto> getPrestamosAmortizados(CuentaDto cuenta) {
		return repository.findByCuentaAndPagado(modelMapper.map(cuenta, Cuenta.class), true).stream()
				.map(p -> modelMapper.map(p, PrestamoDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Prestamo createPrestamo(CreatePrestamoForm dto, CuentaDto dtoCuenta) throws PrestamoActivoException {
		Cuenta cuenta = modelMapper.map(dtoCuenta, Cuenta.class);
		List<Prestamo> prestamos = repository.findByCuentaAndPagado(cuenta, false);

		if (prestamos.size() != 0) {
			throw new PrestamoActivoException("No puedes crear un prestamo si tienes uno activo.");
		}

		Prestamo prestamo = modelMapper.map(dto, Prestamo.class);
		prestamo.setFecha(Calendar.getInstance().getTime());
		prestamo.setCuenta(cuenta);
		return repository.save(prestamo);
	}

	@Override
	public List<Prestamo> getAllPrestamosVivos() {
		return repository.findByPagado(false);
	}

	@Override
	public void updatePrestamos(List<Prestamo> prestamos) {
		repository.saveAll(prestamos);
	}

	@Override
	public ReportsPrestamosDto findByIdReport(Integer id) {
		Prestamo prestamo = repository.findById(id).get();
		ReportsPrestamosDto dto = modelMapper.map(prestamo, ReportsPrestamosDto.class);
		dto.setCliente(modelMapper.map(prestamo.getCuenta().getCliente(), ClienteDto.class));

		return dto;

	}

	@Override
	public List<ReportsListPrestamoDto> getAllPrestamosVivosReports() {
		List<Prestamo> prestamos = repository.findByPagado(false);
		List<ReportsListPrestamoDto> dtos = prestamos.stream().map(p -> {
			ReportsListPrestamoDto dto = modelMapper.map(p, ReportsListPrestamoDto.class);
			dto.setCliente(modelMapper.map(p.getCuenta().getCliente(), ClienteDto.class));
			dto.getCliente().setPass("*******");
			dto.setCuenta(modelMapper.map(p.getCuenta(), ReportsCuentaListDto.class));
			return dto;
		}).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public List<ReportsListPrestamoDto> getAllPrestamosAmortizadosReports() {
		List<Prestamo> prestamos = repository.findByPagado(true);
		List<ReportsListPrestamoDto> dtos = prestamos.stream().map(p -> {
			ReportsListPrestamoDto dto = modelMapper.map(p, ReportsListPrestamoDto.class);
			dto.setCliente(modelMapper.map(p.getCuenta().getCliente(), ClienteDto.class));
			dto.getCliente().setPass("*******");
			dto.setCuenta(modelMapper.map(p.getCuenta(), ReportsCuentaListDto.class));
			return dto;
		}).collect(Collectors.toList());
		return dtos;
	}

	@Override
	public void findByIdReportPDF(Integer id) throws IOException, MessagingException {
		Locale locale = new Locale("es", "ES");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		Prestamo prestamo = repository.findById(id).get();
		Cliente cliente = prestamo.getCuenta().getCliente();

		PdfWriter writer = new PdfWriter(DEST.concat(cliente.getUsuario() + prestamo.getId() + ".pdf"));
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf, PageSize.A4.rotate());
		document.setMargins(20, 20, 20, 20);

		// Funtes
		PdfFont titulo = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
		PdfFont subTitulo = PdfFontFactory.createFont(FontConstants.TIMES_BOLDITALIC);

		// Tabla con los datos del cliente
		Table datosCliente = new Table(new float[] { 1F, 1F }).setMargin(20);
		datosCliente.addCell(new Cell().add("Nombre: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getNombre()).setBorder(Border.NO_BORDER));

		datosCliente.addCell(new Cell().add("Usuario: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getUsuario()).setBorder(Border.NO_BORDER));

		datosCliente.addCell(new Cell().add("Email: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getEmail()).setBorder(Border.NO_BORDER));

		// Titulo del documento
		document.add(new Paragraph("EOI BANK").setFont(titulo).setFontSize(30));
		document.add(new Paragraph("Prestamos").setFont(subTitulo).setFontSize(20).setFontColor(Color.GRAY)
				.setMarginTop(-15));
		document.add(datosCliente);

		// Datos del prestamo
		document.add(new Paragraph("Datos del prestamo").setFont(subTitulo).setFontSize(20));

		Table datosPrestamo = new Table(new float[] { 1F, 1F });
		datosPrestamo.addCell(new Cell().add("Descripción: ").setBorder(Border.NO_BORDER));
		datosPrestamo.addCell(new Cell().add(prestamo.getDescripcion()).setBorder(Border.NO_BORDER));

		datosPrestamo.addCell(new Cell().add("Fecha: ").setBorder(Border.NO_BORDER));
		datosPrestamo.addCell(new Cell().add(dateFormat.format(prestamo.getFecha())).setBorder(Border.NO_BORDER));

		datosPrestamo.addCell(new Cell().add("Importe: ").setBorder(Border.NO_BORDER));
		datosPrestamo.addCell(new Cell().add(String.valueOf(prestamo.getImporte())).setBorder(Border.NO_BORDER));

		datosPrestamo.addCell(new Cell().add("Pagado: ").setBorder(Border.NO_BORDER));
		if (prestamo.isPagado()) {
			datosPrestamo.addCell(new Cell().add("Si").setBorder(Border.NO_BORDER));
		} else {
			datosPrestamo.addCell(new Cell().add("No").setBorder(Border.NO_BORDER));
		}

		datosPrestamo.addCell(new Cell().add("Plazos: ").setBorder(Border.NO_BORDER));
		datosPrestamo.addCell(new Cell().add(String.valueOf(prestamo.getPlazos())).setBorder(Border.NO_BORDER));

		// Datos de las amortizaciones
		for (int i = 0; i < prestamo.getAmortizaciones().size(); i++) {
			Amortizacion amortizacion = prestamo.getAmortizaciones().get(i);

			Table table = new Table(new float[] { 1F, 1F, 1F });
			table.setWidthPercent(100);
			table.addCell(new Cell().add("Fecha"));
			table.addCell(new Cell().add("Importe"));
			table.addCell(new Cell().add("Pagado"));

			table.addCell(new Cell().add(dateFormat.format(amortizacion.getFecha())));
			table.addCell(new Cell().add(String.valueOf(amortizacion.getImporte())));
			if (amortizacion.isPagado()) {
				table.addCell(new Cell().add("Si"));
			} else {
				table.addCell(new Cell().add("No"));
			}

			document.add(table);
		}

		document.close();
		email.sendMail(cliente.getEmail(), "Datos de Prestamo", "Datos del Prestamo: " + prestamo.getId() , DEST, cliente.getUsuario() + prestamo.getId() + ".pdf");
	}
}
