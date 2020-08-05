package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.feign.CuentaInterfaceFeign;
import es.eoi.mundobancario.feign.PrestamoInterfaceFeign;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.service.PrestamoServiceImpl;

@RestController
public class PrestamoController implements PrestamoInterfaceFeign{

	@Autowired
	private PrestamoServiceImpl service;
	@Autowired
	private CuentaInterfaceFeign microCuenta;

	@GetMapping("cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> findByCuenta(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = service.findByCuenta(microCuenta.findById(id).getBody());
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> getPrestamosVivos(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = service.getPrestamosVivos(microCuenta.findById(id).getBody());
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> getPrestamosAmortizados(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = service.getPrestamosAmortizados(microCuenta.findById(id).getBody());
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("cuentas/{id}/prestamos")
	public ResponseEntity<Prestamo> createPrestamo(@RequestBody CreatePrestamoForm form, @PathVariable Integer id) {
		try {
			Prestamo prestamo = service.createPrestamo(form, microCuenta.findById(id).getBody());
			return ResponseEntity.ok(prestamo);
		} catch (Exception e) {
			return new ResponseEntity<Prestamo>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("prestamsos/vivos")
	public ResponseEntity<List<Prestamo>> getAllPrestamosVivos() {
		try {
			List<Prestamo> prestamos = service.getAllPrestamosVivos();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<Prestamo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reports/prestamos/{id}")
	public ResponseEntity<ReportsPrestamosDto> findByIdReport(@PathVariable Integer id){
		try {
			ReportsPrestamosDto report = service.findByIdReport(id);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
			return new ResponseEntity<ReportsPrestamosDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reports/prestamosVivos")
	public ResponseEntity<List<ReportsListPrestamoDto>> getAllPrestamosVivosReports(){
		try {
			List<ReportsListPrestamoDto> reports = service.getAllPrestamosVivosReports();
			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return new ResponseEntity<List<ReportsListPrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reports/prestamosAmortizados")
	public ResponseEntity<List<ReportsListPrestamoDto>> getAllPrestamosAmortizadosReports(){
		try {
			List<ReportsListPrestamoDto> reports = service.getAllPrestamosAmortizadosReports();
			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return new ResponseEntity<List<ReportsListPrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/reports/prestamos/{id}")
	public ResponseEntity<String> findByIdReportPDF(@PathVariable Integer id){
		try {
			service.findByIdReportPDF(id);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

}
