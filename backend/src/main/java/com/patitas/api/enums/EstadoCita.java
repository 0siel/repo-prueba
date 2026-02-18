package com.patitas.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoCita {
	PENDIENTE(1L, "Cita registrada, pendiente de ser atendida"),
	ATENDIDA(2L, "Cita atendida y finalizada"),
	CANCELADA(3L, "Cita cancelada por un daministrador");
	
	private final Long code;
	private final String description;
	
	public static EstadoCita fromCode(Long code){
		for(EstadoCita estado: EstadoCita.values()) {
			if(estado.getCode().equals(code)) {
				return estado;
			}
		}
		throw new IllegalArgumentException("Codigo de estado de cita no válido");
		
	}
}
