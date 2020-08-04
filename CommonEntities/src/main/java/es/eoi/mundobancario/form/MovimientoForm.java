package es.eoi.mundobancario.form;

import es.eoi.mundobancario.enums.TipoMovimientoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoForm {

	private String descripcion;
	private Double importe;
	private TipoMovimientoEnum tipo;
	
}
