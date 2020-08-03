package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentas")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num_cuenta;
	@Column
	private String alias;
	@Column
	private Double saldo;
	
	@ManyToOne(targetEntity = Cliente.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Movimiento.class, cascade = CascadeType.ALL, mappedBy = "cuenta")
	private List<Movimiento> movimientos;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Prestamo.class, cascade = CascadeType.ALL, mappedBy = "cuenta")
	private List<Prestamo> prestamos;
	
	
}
