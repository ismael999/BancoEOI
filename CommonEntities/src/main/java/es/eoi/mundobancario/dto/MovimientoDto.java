package es.eoi.mundobancario.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDto {

	@JsonIgnore
	private int id;
	private String descripcion;
	private Date fecha;
	private Double importe;
	private TipoMovimientoDto tipo;
}
