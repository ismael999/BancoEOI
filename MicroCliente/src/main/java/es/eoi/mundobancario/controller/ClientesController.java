package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.feign.ClienteInterfaceFeign;
import es.eoi.mundobancario.feign.CuentaInterfaceFeign;
import es.eoi.mundobancario.form.Login;
import es.eoi.mundobancario.form.UpdateClienteForm;
import es.eoi.mundobancario.service.ClienteServiceImpl;

@RestController
public class ClientesController implements ClienteInterfaceFeign{

	@Autowired
	private ClienteServiceImpl service;
	@Autowired
	private CuentaInterfaceFeign microCuenta;
	
	@GetMapping("clientes")
	public ResponseEntity<List<ClienteDto>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("clientes/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
		try {
			ClienteDto cliente = service.findById(id);
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			return new ResponseEntity<ClienteDto>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> getCuentas(@PathVariable Integer id) {
		try {
			List<CuentaDto> cuentas = microCuenta.getCuentasFromCliente(id).getBody();
			return ResponseEntity.ok(cuentas);
		} catch (Exception e) {
			return new ResponseEntity<List<CuentaDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("clientes/login")
	public ResponseEntity<ClienteDto> login(@RequestBody Login login) {

		try {
			ClienteDto cliente = service.findByUsuarioAndPass(login.getUsername(), login.getPassword());
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			return new ResponseEntity<ClienteDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("clientes/{id}")
	public ResponseEntity<String> updateEmail(@PathVariable Integer id, @RequestBody UpdateClienteForm email) {

		try {
			service.updateEmail(email.getEmail(), id);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}

	}

	@PostMapping("/clientes")
	public ResponseEntity<String> create(@RequestBody ClienteDto cliente) {
		try {
			service.create(cliente);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/reports/clientes/{id}")
	public ResponseEntity<ReportsClienteDto> findByIdReport(@PathVariable Integer id){
		try {
			ReportsClienteDto report = service.findByIdReport(id);
			return ResponseEntity.ok(report);
		} catch (Exception e) {
			return new ResponseEntity<ReportsClienteDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/reports/clientes/{id}")
	public ResponseEntity<String> findByIdReportPDF(@PathVariable Integer id){
		try {
			service.findByIdReportPDF(id);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
