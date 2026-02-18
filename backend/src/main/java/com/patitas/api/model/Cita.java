package com.patitas.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;

import com.patitas.api.enums.EstadoCita;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "citas")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El nombre del cliente es obligatotio")
	@Size(max = 100, message = "El nombre del cliente no debe exceder los 100 caracteres")
	@Column(name = "nombre_cliente", nullable = false, length = 100)
	private String nombreCliente;
	
	@NotBlank(message = "El nombre de la mascota es requerido")
	@Size(max = 100, message = "El nombre de la mascota no debe exceder los 100 caracteres")
	@Column(name = "nombre_mascota", nullable = false, length = 100)
	private String nombreMascota;
	
	@NotBlank(message = "La razón de la cita es obligatoria")
	@Size(max = 500, message = "La razón de la cita no debe exceder los 500 caracteres")
	@Column(name = "razon_cita", nullable = false, length = 500 )
	private String razonCita;
	
	@NotNull(message = "El estado de la cita es obligatorio")
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_cita", nullable = false)
	private EstadoCita estadoCita = EstadoCita.PENDIENTE;
	
	@NotNull(message = "La fecha y hora de la cita son obligatorias")
	@Column(name = "fecha_cita", nullable = false)
	private LocalDateTime fechaCita;
	
	

}
