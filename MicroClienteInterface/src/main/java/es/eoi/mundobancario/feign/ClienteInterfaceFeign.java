package es.eoi.mundobancario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.form.Login;
import es.eoi.mundobancario.form.UpdateClienteForm;

@FeignClient("micro-cliente")
public interface ClienteInterfaceFeign {

	@GetMapping("clientes")
	public ResponseEntity<List<ClienteDto>> findAll();

	@GetMapping("clientes/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) ;

	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> getCuentas(@PathVariable Integer id) ;

	@PostMapping("clientes/login")
	public ResponseEntity<ClienteDto> login(@RequestBody Login login);

	@PutMapping("clientes/{id}")
	public ResponseEntity<String> updateEmail(@PathVariable Integer id, @RequestBody UpdateClienteForm email);

	@PostMapping("/clientes")
	public ResponseEntity<String> create(@RequestBody ClienteDto cliente);
	
	@GetMapping("/reports/clientes/{id}")
	public ResponseEntity<ReportsClienteDto> findByIdReport(@PathVariable Integer id);
	
	@PostMapping("/reports/clientes/{id}")
	public ResponseEntity<String> findByIdReportPDF(@PathVariable Integer id);
	
}
