package es.eoi.mundobancario.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePrestamoForm {

	private String descripcion;
	private Double importe;
	private int plazos;
	
}
