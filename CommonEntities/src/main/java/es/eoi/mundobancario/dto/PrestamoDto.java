package es.eoi.mundobancario.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoDto {

	@JsonIgnore
	private int id;
	private String descripcion;
	private Date fecha;
	private Double importe;
	private int plazos;
	private boolean pagado;
	private List<AmortizacionDto> amortizaciones;
	@JsonIgnore
	private CuentaDto cuenta;
}
