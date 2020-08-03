package es.eoi.mundobancario.serviceInterfaces;

import java.util.List;

import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;

public interface AmortizacionService {

	public void create(Prestamo prestamo);
	
	public List<Movimiento> amortizar(List<Prestamo> prestamo);
	
}
