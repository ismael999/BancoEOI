package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String usuario;
	@Column
	private String pass;
	@Column
	private String nombre;
	@Column
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Cuenta.class, cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Cuenta> cuentas;
	
}
