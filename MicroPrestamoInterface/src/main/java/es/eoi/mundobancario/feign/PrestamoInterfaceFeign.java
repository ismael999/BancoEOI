package es.eoi.mundobancario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.form.CreatePrestamoForm;

@FeignClient("micro-prestamo")
public interface PrestamoInterfaceFeign {

	@GetMapping("cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> findByCuenta(@PathVariable Integer id);
	
	@GetMapping("cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> getPrestamosVivos(@PathVariable Integer id);
	
	@GetMapping("cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> getPrestamosAmortizados(@PathVariable Integer id);
	
	@PostMapping("cuentas/{id}/prestamos")
	public ResponseEntity<Prestamo> createPrestamo(@RequestBody CreatePrestamoForm form, @PathVariable Integer id);
	
	@GetMapping("prestamsos/vivos")
	public ResponseEntity<List<Prestamo>> getAllPrestamosVivos();
	
	@GetMapping("/reports/prestamos/{id}")
	public ResponseEntity<ReportsPrestamosDto> findByIdReport(@PathVariable Integer id);
	
	@GetMapping("/reports/prestamosVivos")
	public ResponseEntity<List<ReportsListPrestamoDto>> getAllPrestamosVivosReports();
	
	@GetMapping("/reports/prestamosAmortizados")
	public ResponseEntity<List<ReportsListPrestamoDto>> getAllPrestamosAmortizadosReports();
	
	@PostMapping("/reports/prestamos/{id}")
	public ResponseEntity<String> findByIdReportPDF(@PathVariable Integer id);
}
