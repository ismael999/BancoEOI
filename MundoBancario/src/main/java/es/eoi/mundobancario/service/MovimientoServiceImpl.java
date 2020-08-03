package es.eoi.mundobancario.service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;
import es.eoi.mundobancario.enums.TipoMovimientoEnum;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.repository.MovimientoRepository;
import es.eoi.mundobancario.serviceInterfaces.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoRepository repository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<MovimientoDto> findById(Integer id) {
		return null;
	}

	@Override
	public List<MovimientoDto> findByCuenta(CuentaDto cuenta) {
		List<Movimiento> movimiento = repository.findByCuenta(modelMapper.map(cuenta, Cuenta.class));
		
		return movimiento
				.stream()
				.map(m -> modelMapper.map(m, MovimientoDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createMovimiento(MovimientoForm form, CuentaDto cuenta, TipoMovimientoEnum tipoId) {
		TipoMovimiento tipo = new TipoMovimiento();
		tipo.setId(tipoId.getId());
		
		Movimiento movimiento = modelMapper.map(form, Movimiento.class);
		movimiento.setCuenta(modelMapper.map(cuenta, Cuenta.class));
		movimiento.setTipo(tipo);
		movimiento.setFecha(Calendar.getInstance().getTime());
		repository.save(movimiento);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createMovimiento(Prestamo prestamo, CuentaDto cuenta) {
		TipoMovimiento tipo = new TipoMovimiento();
		tipo.setId(TipoMovimientoEnum.PRESTAMO.getId());
		
		Movimiento movimiento = new Movimiento();
		movimiento.setCuenta(modelMapper.map(cuenta, Cuenta.class));
		movimiento.setDescripcion(prestamo.getDescripcion());
		movimiento.setFecha(prestamo.getFecha());
		movimiento.setImporte(prestamo.getImporte());
		movimiento.setTipo(tipo);
		repository.save(movimiento);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createMovimiento(Prestamo prestamo, Cuenta cuenta) {
		TipoMovimiento tipo = new TipoMovimiento();
		tipo.setId(TipoMovimientoEnum.PRESTAMO.getId());
		
		Movimiento movimiento = new Movimiento();
		movimiento.setCuenta(cuenta);
		movimiento.setDescripcion(prestamo.getDescripcion());
		movimiento.setFecha(prestamo.getFecha());
		movimiento.setImporte(prestamo.getImporte());
		movimiento.setTipo(tipo);
		repository.save(movimiento);
	}

	@Override
	public void createMovimientos(List<Movimiento> movimientos) {
		repository.saveAll(movimientos);
	}

}
