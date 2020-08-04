package es.eoi.mundobancario.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "movimientos")
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String descripcion;
	@Column
	private Date fecha;
	@Column
	private Double importe;
	
	@ManyToOne(targetEntity = Cuenta.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cuenta")
	private Cuenta cuenta;
	
	@ManyToOne(targetEntity = TipoMovimiento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo")
	private TipoMovimiento tipo;
	
}
