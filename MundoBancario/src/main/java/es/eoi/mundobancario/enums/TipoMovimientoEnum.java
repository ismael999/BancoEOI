package es.eoi.mundobancario.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoMovimientoEnum {

	INGRESO(1),PRESTAMO(2),PAGO(3),AMOTIZACION(4),INTERES(5);
	
	private int id;
	
}
