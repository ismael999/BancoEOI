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

import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.feign.CuentaInterfaceFeign;
import es.eoi.mundobancario.feign.MovimientoInterfaceFeign;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.service.MovimientoServiceImpl;

@RestController
public class MovimientoController implements MovimientoInterfaceFeign{
	
	@Autowired
	private MovimientoServiceImpl service;
	@Autowired
	private CuentaInterfaceFeign microCuenta;

	@GetMapping("cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> findByCuenta(@PathVariable Integer id){
		try {
			List<MovimientoDto> movimientos = service.findByCuenta(microCuenta.findById(id).getBody());
			return ResponseEntity.ok(movimientos);
		} catch (Exception e) {
			return new ResponseEntity<List<MovimientoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("cuentas/{id}/prestamo")
	public ResponseEntity<String> createMovimientoPrestamo(@RequestBody Prestamo prestamo, @PathVariable Integer id){
		try {
			service.createMovimiento(prestamo, microCuenta.findById(id).getBody());
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("cuentas/{id}/movimientos")
	public ResponseEntity<String> createMovimiento(@RequestBody MovimientoForm form, @PathVariable Integer id){
		try {
			service.createMovimiento(form, microCuenta.findById(id).getBody());
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("movimientos")
	public ResponseEntity<String> createMovimientos(@RequestBody List<Movimiento> movimientos){
		try {
			service.createMovimientos(movimientos);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
