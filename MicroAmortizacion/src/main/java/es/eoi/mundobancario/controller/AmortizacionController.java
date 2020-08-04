package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.feign.AmortizacionInterfaceFeign;
import es.eoi.mundobancario.service.AmortizacionServiceImpl;

@RestController
public class AmortizacionController implements AmortizacionInterfaceFeign{

	@Autowired
	private AmortizacionServiceImpl service;
	
	@PostMapping("amortizaciones")
	public ResponseEntity<String> create(@RequestBody Prestamo prestamo){
		try {
			service.create(prestamo);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("amortizaciones/amortizar")
	public ResponseEntity<List<Movimiento>> amortizar(@RequestBody List<Prestamo> prestamos){
		try {
			List<Movimiento> movimientos = service.amortizar(prestamos);
			return ResponseEntity.ok(movimientos);
		} catch (Exception e) {
			return new ResponseEntity<List<Movimiento>>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
