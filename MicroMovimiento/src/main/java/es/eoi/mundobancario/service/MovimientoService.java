package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.form.MovimientoForm;

public interface MovimientoService {

	
	public void createMovimiento(MovimientoForm form, CuentaDto cuenta);
	
	public void createMovimiento(Prestamo prestamo, CuentaDto cuenta);
	
	public void createMovimiento(Prestamo prestamo, Cuenta cuenta);
	
	public List<MovimientoDto> findByCuenta(CuentaDto cuenta);
	
	public void createMovimientos(List<Movimiento> movimientos);
	
}
