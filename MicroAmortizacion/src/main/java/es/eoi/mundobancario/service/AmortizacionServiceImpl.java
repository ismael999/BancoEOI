package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.enums.TipoMovimientoEnum;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@Service
public class AmortizacionServiceImpl implements AmortizacionService {

	@Autowired
	private AmortizacionRepository repository;

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Prestamo prestamo) {

		prestamo.setAmortizaciones(new ArrayList<Amortizacion>());
		Double importe = prestamo.getImporte() / prestamo.getPlazos();

		for (int i = 0; i < prestamo.getPlazos(); i++) {

			Date fecha = Calendar.getInstance().getTime();
			fecha.setMonth(fecha.getMonth() + (i + 1));
			Amortizacion amortizacion = new Amortizacion();
			amortizacion.setImporte(importe);
			amortizacion.setFecha(fecha);
			amortizacion.setPrestamo(prestamo);

			prestamo.getAmortizaciones().add(amortizacion);
		}

		repository.saveAll(prestamo.getAmortizaciones());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Movimiento> amortizar(List<Prestamo> prestamos) {
		Date fechaHoy = Calendar.getInstance().getTime();
		List<Movimiento> movimientos = new ArrayList<Movimiento>();
		List<Amortizacion> amortizacionesToUpdate = new ArrayList<Amortizacion>();
		
		for (Prestamo prestamo : prestamos) {
			List<Amortizacion> amortizaciones = prestamo.getAmortizaciones();
			
			for (Amortizacion amortizacion : amortizaciones) {
				if(amortizacion.getFecha().compareTo(fechaHoy) <= 0) {
					if(!amortizacion.isPagado()) {
						
						// Movimiento de amortización
						TipoMovimiento tipoAm = new TipoMovimiento();
						tipoAm.setId(TipoMovimientoEnum.AMOTIZACION.getId());
						
						Movimiento importe = new Movimiento();
						importe.setCuenta(prestamo.getCuenta());
						importe.setDescripcion("Amortización");
						importe.setFecha(fechaHoy);
						importe.setImporte(amortizacion.getImporte());
						importe.setTipo(tipoAm);
						movimientos.add(importe);
						
						// Movimiento de interes
						Double importeInteres = 2 * amortizacion.getImporte() / 100;
						TipoMovimiento tipoIn = new TipoMovimiento();
						tipoIn.setId(TipoMovimientoEnum.INTERES.getId());
						
						Movimiento interes = new Movimiento();
						interes.setCuenta(prestamo.getCuenta());
						interes.setDescripcion("Interes");
						interes.setFecha(fechaHoy);
						interes.setImporte(importeInteres);
						interes.setTipo(tipoIn);
						movimientos.add(interes);
						
						// Amortización a updatear
						amortizacion.setPagado(true);
						amortizacionesToUpdate.add(amortizacion);
					}
				}
			}
		}
		repository.saveAll(amortizacionesToUpdate);
		return movimientos;
	}
}
