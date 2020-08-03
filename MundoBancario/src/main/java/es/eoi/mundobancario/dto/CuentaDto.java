package es.eoi.mundobancario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {

	private int num_cuenta;
	private String alias;
	private Double saldo;
	private ClienteDto cliente;
		
}
