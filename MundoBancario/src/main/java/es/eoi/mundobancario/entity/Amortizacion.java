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
@Table(name = "amortizaciones")
public class Amortizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private Date fecha;
	@Column
	private Double importe;
	@Column
	private boolean pagado;
	
	@ManyToOne(targetEntity = Prestamo.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestamo")
	private Prestamo prestamo;
}
