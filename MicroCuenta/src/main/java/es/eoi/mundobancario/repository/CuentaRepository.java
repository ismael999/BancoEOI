package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{

	public List<Cuenta> findBySaldoLessThanEqual(Double saldo);
	
	public List<Cuenta> findByCliente(Cliente cliente);
	
}
