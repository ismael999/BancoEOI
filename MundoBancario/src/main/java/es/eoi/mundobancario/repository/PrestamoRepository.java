package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{

	public List<Prestamo> findByCuenta(Cuenta cuenta);
	
	public List<Prestamo> findByCuentaAndPagado(Cuenta cuenta, boolean pagado);
	
	public List<Prestamo> findByPagado(boolean pagado);
	
}
