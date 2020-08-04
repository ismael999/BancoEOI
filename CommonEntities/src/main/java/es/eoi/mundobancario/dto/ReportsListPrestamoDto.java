package es.eoi.mundobancario.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsListPrestamoDto {

	private String descripcion;
	private Date fecha;
	private Double importe;
	private int plazos;
	private boolean pagado;
	private ReportsCuentaListDto cuenta;
	private ClienteDto cliente;
	
}
