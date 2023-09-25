package dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private String carrera;
    private BigInteger anio;
    private BigInteger graduado;
    private BigInteger registrado;

	public ReporteDTO(String carrera, BigInteger anio, BigInteger graduado, BigInteger registrado) {
		super();
		this.carrera = carrera;
		this.anio = anio;
		this.graduado = graduado;
		this.registrado = registrado;
	}

	@Override
	public String toString() {
		return "ReporteDTO [carrera=" + carrera + ", anio=" + anio + ", graduado=" + graduado + ", registrado="
				+ registrado + "]";
	}
	
}
