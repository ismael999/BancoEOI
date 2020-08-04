package es.eoi.mundobancario.service;

import java.util.List;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.exceptions.SaldoInsuficienteException;
import es.eoi.mundobancario.form.CreateCuentaForm;

public interface CuentaService {

	public List<CuentaDto> findAll();
	
	public List<CuentaDto> findBySaldoNegativo();
	
	public CuentaDto findById(Integer id);
	
	public Cuenta findCuentaById(Integer id);
	
	public List<CuentaDto> findByUsuario(ClienteDto cliente);
	
	public void create(CreateCuentaForm cuenta, ClienteDto cliente);
	
	public void updateAlias(String alias, Integer id);
	
	public void ingresar(CuentaDto cuenta, Double importe);

	public void ingresar(Cuenta cuenta, Double importe);
	
	public void retirar(CuentaDto cuenta, Double importe) throws SaldoInsuficienteException;
	
	public void retirarAmortizacion(List<Movimiento> movimientos);
	
		
}
