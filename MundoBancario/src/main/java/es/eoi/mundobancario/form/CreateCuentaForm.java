package es.eoi.mundobancario.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCuentaForm {

	private String alias;
	private Double saldo;
	private Integer idCliente;
	
}
