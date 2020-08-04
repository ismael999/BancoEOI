package es.eoi.mundobancario.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsAmortizacionDto {

	private Date fecha;
	private Double importe;
	private boolean pagado;	
}
