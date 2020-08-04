package es.eoi.mundobancario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;

@FeignClient("micro-amortizacion")
public interface AmortizacionInterfaceFeign {

	@PostMapping("amortizaciones")
	public ResponseEntity<String> create(@RequestBody Prestamo prestamo);
	
	@PostMapping("amortizaciones/amortizar")
	public ResponseEntity<List<Movimiento>> amortizar(@RequestBody List<Prestamo> prestamos);
	
}
