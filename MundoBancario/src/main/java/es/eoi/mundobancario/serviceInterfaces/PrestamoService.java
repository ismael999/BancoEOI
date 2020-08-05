package es.eoi.mundobancario.serviceInterfaces;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.exceptions.PrestamoActivoException;
import es.eoi.mundobancario.form.CreatePrestamoForm;

public interface PrestamoService {

	public List<PrestamoDto> findByCuenta(CuentaDto cuenta);
	
	public ReportsPrestamosDto findByIdReport(Integer id);
	
	public void findByIdReportPDF(Integer id) throws IOException, MessagingException;
	
	public List<PrestamoDto> getPrestamosVivos(CuentaDto cuenta);
	
	public List<Prestamo> getAllPrestamosVivos();
	
	public List<ReportsListPrestamoDto> getAllPrestamosVivosReports();
	
	public List<ReportsListPrestamoDto> getAllPrestamosAmortizadosReports();
	
	public List<PrestamoDto> getPrestamosAmortizados(CuentaDto cuenta);
	
	public Prestamo createPrestamo(CreatePrestamoForm prestamo, Cuenta cuenta) throws PrestamoActivoException;
	
	public void updatePrestamos(List<Prestamo> prestamos);
	
}
