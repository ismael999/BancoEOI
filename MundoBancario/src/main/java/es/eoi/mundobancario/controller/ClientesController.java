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
import es.eoi.mundobancario.form.Login;
import es.eoi.mundobancario.form.UpdateClienteForm;
import es.eoi.mundobancario.service.ClienteServiceImpl;
import es.eoi.mundobancario.serviceInterfaces.CuentaService;

@RestController
public class ClientesController {

	@Autowired
	private ClienteServiceImpl clienteService;
	@Autowired
	private CuentaService cuentaService;

	@GetMapping("clientes")
	public ResponseEntity<List<ClienteDto>> findAll() {
		return ResponseEntity.ok(clienteService.findAll());
	}

	@GetMapping("clientes/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
		try {
			ClienteDto cliente = clienteService.findById(id);
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			return new ResponseEntity<ClienteDto>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> getCuentas(@PathVariable Integer id) {
		try {
			List<CuentaDto> cuentas = cuentaService.findByUsuario(clienteService.findById(id));
			return ResponseEntity.ok(cuentas);
		} catch (Exception e) {
			return new ResponseEntity<List<CuentaDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("clientes/login")
	public ResponseEntity<ClienteDto> login(@RequestBody Login login) {

		try {
			ClienteDto cliente = clienteService.findByUsuarioAndPass(login.getUsername(), login.getPassword());
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			return new ResponseEntity<ClienteDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("clientes/{id}")
	public ResponseEntity<String> updateEmail(@PathVariable Integer id, @RequestBody UpdateClienteForm email) {

		try {
			clienteService.updateEmail(email.getEmail(), id);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}

	}

	@PostMapping("/clientes")
	public ResponseEntity<String> create(@RequestBody ClienteDto cliente) {
		try {
			clienteService.create(cliente);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

	}

}
