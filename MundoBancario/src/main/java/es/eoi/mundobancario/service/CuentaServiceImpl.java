package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.exceptions.SaldoInsuficienteException;
import es.eoi.mundobancario.form.CreateCuentaForm;
import es.eoi.mundobancario.repository.CuentaRepository;
import es.eoi.mundobancario.serviceInterfaces.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService{

	@Autowired
	private CuentaRepository repository;
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<CuentaDto> findAll() {
		return repository.findAll()
				.stream()
				.map(c -> {
					CuentaDto cuenta = modelMapper.map(c, CuentaDto.class);
					cuenta.getCliente().setPass("*****");
					return cuenta;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<CuentaDto> findBySaldoNegativo() {
		return repository.findBySaldoLessThanEqual(0.0)
				.stream().map(c -> {
					CuentaDto cuenta = modelMapper.map(c, CuentaDto.class);
					cuenta.getCliente().setPass("*****");
					return cuenta;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CuentaDto findById(Integer id) {
		CuentaDto cuenta = modelMapper.map(repository.findById(id).get(), CuentaDto.class);
		cuenta.getCliente().setPass("*****");
		return cuenta;
	}

	@Override
	public List<CuentaDto> findByUsuario(ClienteDto clienteDto) {
		Cliente cliente = modelMapper.map(clienteDto, Cliente.class);
		List<CuentaDto> cuentas = repository.findByCliente(cliente)
				.stream()
				.map(c -> modelMapper.map(c, CuentaDto.class))
				.collect(Collectors.toList());
		return cuentas;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(CreateCuentaForm dto, ClienteDto cliente) {
		Cuenta cuenta = modelMapper.map(dto, Cuenta.class);
		cuenta.setCliente(modelMapper.map(cliente, Cliente.class));
		repository.save(cuenta);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void updateAlias(String alias, Integer id) {
		Cuenta cuenta = repository.findById(id).get();
		cuenta.setAlias(alias);
		
		repository.save(cuenta);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void ingresar(CuentaDto cuenta, Double importe) {
		cuenta.setSaldo(cuenta.getSaldo() + importe);
		repository.save(modelMapper.map(cuenta, Cuenta.class));
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void retirar(CuentaDto cuenta, Double importe) throws SaldoInsuficienteException {
		
		if(cuenta.getSaldo() <= 0) {
			throw new SaldoInsuficienteException("El saldo es igual o inferior a 0");
		}else {
			cuenta.setSaldo(cuenta.getSaldo() - importe);
			repository.save(modelMapper.map(cuenta, Cuenta.class));
		}
	}

	@Override
	public Cuenta findCuentaById(Integer id) {
		Cuenta cuenta = repository.findById(id).get();
		return cuenta;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void ingresar(Cuenta cuenta, Double importe) {
		cuenta.setSaldo(cuenta.getSaldo() + importe);
		repository.save(cuenta);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void retirarAmortizacion(List<Movimiento> movimientos) {
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		
		for (Movimiento movimiento : movimientos) {
			Cuenta cuenta = movimiento.getCuenta();
			cuenta.setSaldo(cuenta.getSaldo() - movimiento.getImporte());
			
			if(!cuentas.contains(cuenta)) {
				cuentas.add(cuenta);
			}
		}
		repository.saveAll(cuentas);
	}
}
