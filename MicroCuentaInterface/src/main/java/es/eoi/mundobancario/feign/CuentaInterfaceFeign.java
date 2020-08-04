package es.eoi.mundobancario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.form.CreateCuentaForm;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.form.UpdateCuentaForm;

@FeignClient("micro-cuenta")
public interface CuentaInterfaceFeign {

	@GetMapping("cuentas")
	public ResponseEntity<List<CuentaDto>> findAll();

	@GetMapping("cuentas/deudoras")
	public ResponseEntity<List<CuentaDto>> findBySaldoNegativo();

	@GetMapping("cuentas/{id}")
	public ResponseEntity<CuentaDto> findById(@PathVariable Integer id);

	@PostMapping("cuentas")
	public ResponseEntity<String> create(@RequestBody CreateCuentaForm cuenta);

	@PutMapping("cuentas/{id}")
	public ResponseEntity<String> updateAlias(@RequestBody UpdateCuentaForm alias, @PathVariable Integer id);

	@GetMapping("cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> getMovimientos(@PathVariable Integer id);

	@GetMapping("cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> getPrestamos(@PathVariable Integer id);

	@GetMapping("cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> getPrestamosVivos(@PathVariable Integer id);

	@GetMapping("cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> getPrestamosAmortizados(@PathVariable Integer id);

	@PostMapping("cuentas/{id}/prestamos")
	public ResponseEntity<String> createPrestamo(@RequestBody CreatePrestamoForm form, @PathVariable Integer id);

	@PostMapping("cuentas/{id}/ingresos")
	public ResponseEntity<String> createIngreso(@PathVariable Integer id, @RequestBody MovimientoForm form);

	@PostMapping("cuentas/{id}/pagos")
	public ResponseEntity<String> createPago(@PathVariable Integer id, @RequestBody MovimientoForm form);
	
	@PostMapping("cuentas/ejecutarAmortizacionesDiarias")
	public ResponseEntity<String> amortizar();
	
	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> getCuentasFromCliente(@PathVariable Integer id);
	
}
