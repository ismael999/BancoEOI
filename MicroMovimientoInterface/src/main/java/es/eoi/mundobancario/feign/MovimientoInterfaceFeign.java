package es.eoi.mundobancario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.form.MovimientoForm;

@FeignClient("micro-movimiento")
public interface MovimientoInterfaceFeign {

	@GetMapping("cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> findByCuenta(@PathVariable Integer id);
	
	@PostMapping("cuentas/{id}/prestamo")
	public ResponseEntity<String> createMovimientoPrestamo(@RequestBody Prestamo prestamo, @PathVariable Integer id);
	
	@PostMapping("cuentas/{id}/movimientos")
	public ResponseEntity<String> createMovimiento(@RequestBody MovimientoForm form, @PathVariable Integer id);
	
	@PostMapping("movimientos")
	public ResponseEntity<String> createMovimientos(@RequestBody List<Movimiento> movimientos);
}
