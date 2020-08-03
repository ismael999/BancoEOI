package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancario.service.ClienteServiceImpl;
import es.eoi.mundobancario.service.PrestamoServiceImpl;

@RestController
public class ReportsController {

	@Autowired
	private ClienteServiceImpl clienteService;
	@Autowired
	private PrestamoServiceImpl prestamoService;

	@GetMapping("/reports/clientes/{id}")
	public ResponseEntity<ReportsClienteDto> getClienteReport(@PathVariable Integer id) {
		try {
			ReportsClienteDto dto = clienteService.findByIdReport(id);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return new ResponseEntity<ReportsClienteDto>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/reports/prestamos/{id}")
	public ResponseEntity<ReportsPrestamosDto> getPrestamosReport(@PathVariable Integer id) {
		try {
			ReportsPrestamosDto dto = prestamoService.findByIdReport(id);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return new ResponseEntity<ReportsPrestamosDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reports/prestamosVivos")
	public ResponseEntity<List<ReportsListPrestamoDto>> getPrestamosVivosList(){
		try {
			List<ReportsListPrestamoDto> prestamos = prestamoService.getAllPrestamosVivosReports();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<ReportsListPrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reports/prestamosAmortizados")
	public ResponseEntity<List<ReportsListPrestamoDto>> getPrestamosAmortizadosList(){
		try {
			List<ReportsListPrestamoDto> prestamos = prestamoService.getAllPrestamosAmortizadosReports();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<ReportsListPrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/reports/clientes/{id}")
	public ResponseEntity<String> getClienteReportPDF(@PathVariable Integer id) {
		try {
			clienteService.findByIdReportPDF(id);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/reports/prestamos/{id}")
	public ResponseEntity<String> getPrestamosReportPDF(@PathVariable Integer id) {
		try {
			prestamoService.findByIdReportPDF(id);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
}
