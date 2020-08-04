package es.eoi.mundobancario.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsCuentaDto {

	private String alias;
	private Double saldo;
	private List<MovimientoDto> movimientos;
	
}
