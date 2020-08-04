package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.enums.TipoMovimientoEnum;
import es.eoi.mundobancario.feign.AmortizacionInterfaceFeign;
import es.eoi.mundobancario.feign.ClienteInterfaceFeign;
import es.eoi.mundobancario.feign.CuentaInterfaceFeign;
import es.eoi.mundobancario.feign.MovimientoInterfaceFeign;
import es.eoi.mundobancario.feign.PrestamoInterfaceFeign;
import es.eoi.mundobancario.form.CreateCuentaForm;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.form.UpdateCuentaForm;
import es.eoi.mundobancario.service.CuentaServiceImpl;

@RestController
public class CuentasController implements CuentaInterfaceFeign{

	@Autowired
	private CuentaServiceImpl cuentaService;
	@Autowired
	private MovimientoInterfaceFeign microMovimiento;
	@Autowired
	private PrestamoInterfaceFeign microPrestamo;
	@Autowired
	private AmortizacionInterfaceFeign microAmortizacion;
	@Autowired
	private ClienteInterfaceFeign clienteMicro;

	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> getCuentasFromCliente(@PathVariable Integer id){
		try {
			List<CuentaDto> cuentas = cuentaService.findByUsuario(clienteMicro.findById(id).getBody());
			return ResponseEntity.ok(cuentas);
		} catch (Exception e) {
			return new ResponseEntity<List<CuentaDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("cuentas")
	public ResponseEntity<List<CuentaDto>> findAll() {
		return ResponseEntity.ok(cuentaService.findAll());
	}

	@GetMapping("cuentas/deudoras")
	public ResponseEntity<List<CuentaDto>> findBySaldoNegativo() {

		try {
			List<CuentaDto> cuentas = cuentaService.findBySaldoNegativo();
			return ResponseEntity.ok(cuentas);
		} catch (Exception e) {
			return new ResponseEntity<List<CuentaDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}")
	public ResponseEntity<CuentaDto> findById(@PathVariable Integer id) {
		try {
			CuentaDto cuenta = cuentaService.findById(id);
			return ResponseEntity.ok(cuenta);
		} catch (Exception e) {
			return new ResponseEntity<CuentaDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("cuentas")
	public ResponseEntity<String> create(@RequestBody CreateCuentaForm cuenta) {
		try {
			cuentaService.create(cuenta, clienteMicro.findById(cuenta.getIdCliente()).getBody());
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("cuentas/{id}")
	public ResponseEntity<String> updateAlias(@RequestBody UpdateCuentaForm alias, @PathVariable Integer id) {
		try {
			cuentaService.updateAlias(alias.getAlias(), id);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> getMovimientos(@PathVariable Integer id) {
		try {
			List<MovimientoDto> movimientos = microMovimiento.findByCuenta(id).getBody();
			return ResponseEntity.ok(movimientos);
		} catch (Exception e) {
			return new ResponseEntity<List<MovimientoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> getPrestamos(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = microPrestamo.findByCuenta(id).getBody();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> getPrestamosVivos(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = microPrestamo.getPrestamosVivos(id).getBody();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> getPrestamosAmortizados(@PathVariable Integer id) {
		try {
			List<PrestamoDto> prestamos = microPrestamo.getPrestamosAmortizados(id).getBody();
			return ResponseEntity.ok(prestamos);
		} catch (Exception e) {
			return new ResponseEntity<List<PrestamoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("cuentas/{id}/prestamos")
	public ResponseEntity<String> createPrestamo(@RequestBody CreatePrestamoForm form, @PathVariable Integer id) {
		try {

			Cuenta cuenta = cuentaService.findCuentaById(id);
			Prestamo prestamo = microPrestamo.createPrestamo(form, id).getBody();

			microAmortizacion.create(prestamo);

			microMovimiento.createMovimientoPrestamo(prestamo, id);

			cuentaService.ingresar(cuenta, prestamo.getImporte());

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("cuentas/{id}/ingresos")
	public ResponseEntity<String> createIngreso(@PathVariable Integer id, @RequestBody MovimientoForm form) {
		try {
			CuentaDto cuenta = cuentaService.findById(id);
			form.setTipo(TipoMovimientoEnum.INGRESO);
			microMovimiento.createMovimiento(form, id);

			cuentaService.ingresar(cuenta, form.getImporte());

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("cuentas/{id}/pagos")
	public ResponseEntity<String> createPago(@PathVariable Integer id, @RequestBody MovimientoForm form) {
		try {
			CuentaDto cuenta = cuentaService.findById(id);

			cuentaService.retirar(cuenta, form.getImporte());
			form.setTipo(TipoMovimientoEnum.PAGO);
			microMovimiento.createMovimiento(form, id);

			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("cuentas/ejecutarAmortizacionesDiarias")
	public ResponseEntity<String> amortizar(){
		System.out.println("Amortizacion Ejecutada");
		try {
			ejecutarAmortizaciones();
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@Scheduled(cron = "0 0 18 * * *")
	@Transactional
	public void ejecutarAmortizaciones() {
		List<Movimiento> movimientos = microAmortizacion.amortizar(microPrestamo.getAllPrestamosVivos().getBody()).getBody();
		cuentaService.retirarAmortizacion(movimientos);
		microMovimiento.createMovimientos(movimientos);
		System.out.println("Amortizacion Ejecutada");
	}
	
}
