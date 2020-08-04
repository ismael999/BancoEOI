package es.eoi.mundobancario.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.enums.TipoMovimientoEnum;
import es.eoi.mundobancario.repository.ClienteRepository;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private ModelMapper modelMapper;
	
	private final String DEST = "./src/main/resources/EOI_BANK_CLIENTE_";

	@Override
	public List<ClienteDto> findAll() {
		return repository.findAll().stream().map(c -> {
			ClienteDto cliente = modelMapper.map(c, ClienteDto.class);
			cliente.setPass("*******");
			return cliente;
		}).collect(Collectors.toList());
	}

	@Override
	public ClienteDto findById(Integer id) {
		ClienteDto cliente = modelMapper.map(repository.findById(id).get(), ClienteDto.class);
		cliente.setPass("*******");
		return cliente;
	}

	@Override
	public ClienteDto findByUsuarioAndPass(String usuario, String pass) {
		ClienteDto cliente = modelMapper.map(repository.findByUsuarioAndPass(usuario, pass), ClienteDto.class);
		cliente.setPass("*********");
		return cliente;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void updateEmail(String email, Integer id) {
		Cliente cliente = repository.findById(id).get();
		cliente.setEmail(email);
		repository.save(cliente);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(ClienteDto dto) {
		Cliente cliente = modelMapper.map(dto, Cliente.class);

		repository.save(cliente);
	}

	@Override
	public ReportsClienteDto findByIdReport(Integer id) {
		ReportsClienteDto cliente = modelMapper.map(repository.findById(id).get(), ReportsClienteDto.class);
		return cliente;
	}

	@Override
	public void findByIdReportPDF(Integer id) throws IOException {
		Locale locale = new Locale("es", "ES");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		ReportsClienteDto cliente = modelMapper.map(repository.findById(id).get(), ReportsClienteDto.class);
		
		PdfWriter writer = new PdfWriter(DEST.concat(cliente.getUsuario()+".pdf"));
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf, PageSize.A4.rotate());
		document.setMargins(20, 20, 20, 20);
		
		// Funtes
		PdfFont titulo = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
		PdfFont subTitulo = PdfFontFactory.createFont(FontConstants.TIMES_BOLDITALIC);
		
		// Tabla con los datos del cliente
		Table datosCliente = new Table(new float[] {1F, 1F}).setMargin(20);
		datosCliente.addCell(new Cell().add("Nombre: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getNombre()).setBorder(Border.NO_BORDER));
		
		datosCliente.addCell(new Cell().add("Usuario: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getUsuario()).setBorder(Border.NO_BORDER));
		
		datosCliente.addCell(new Cell().add("Email: ").setBorder(Border.NO_BORDER));
		datosCliente.addCell(new Cell().add(cliente.getEmail()).setBorder(Border.NO_BORDER));
		
		// Titulo del documento
		document.add(new Paragraph("EOI BANK").setFont(titulo).setFontSize(30));
		document.add(new Paragraph("Clientes").setFont(subTitulo).setFontSize(20).setFontColor(Color.GRAY).setMarginTop(-15));
		document.add(datosCliente);
		
		// Datos de las cuentas y los movimientos
		document.add(new Paragraph("Cuentas y Movimientos").setFont(subTitulo).setFontSize(20));
		
		for (int i = 0; i < cliente.getCuentas().size(); i++) {
			String alias = cliente.getCuentas().get(i).getAlias();
			Double saldo = cliente.getCuentas().get(i).getSaldo();
			List<MovimientoDto> movimientos = cliente.getCuentas().get(i).getMovimientos();
			document.add(new Paragraph("Cuenta: ".concat(alias).concat(" | Saldo: ").concat(String.valueOf(saldo) + "€")));
			
			if (movimientos.size() == 0) {
				document.add(new Paragraph("No hay movimientos.").setFontColor(Color.GRAY));
			}else {
				for (MovimientoDto movimiento : movimientos) {
					Table table = new Table(new float[] {1F, 1F, 1F, 1F});
					table.setWidthPercent(100);
							
					table.addCell(new Cell().add("Descripción"));
					table.addCell(new Cell().add("Fecha"));
					table.addCell(new Cell().add("Importe"));
					table.addCell(new Cell().add("Tipo"));
					
					table.addCell(new Cell().add(movimiento.getDescripcion()));
					table.addCell(new Cell().add(dateFormat.format(movimiento.getFecha())));
					table.addCell(new Cell().add(String.valueOf(movimiento.getImporte() + "€")));
					if(movimiento.getTipo().getId() == TipoMovimientoEnum.PRESTAMO.getId() || movimiento.getTipo().getId() == TipoMovimientoEnum.INGRESO.getId()) {
						table.addCell(new Cell().add(movimiento.getTipo().getTipo()).setFontColor(Color.GREEN));
					}else {
						table.addCell(new Cell().add(movimiento.getTipo().getTipo()).setFontColor(Color.RED));
					}
					
					document.add(table);
				}
			}
			
		}
		document.close();
	}
}
