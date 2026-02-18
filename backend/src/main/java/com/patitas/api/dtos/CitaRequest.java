package com.patitas.api.dtos;

import java.time.LocalDateTime;

import com.patitas.api.enums.EstadoCita;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CitaRequest(
@NotBlank(message = "El nombre del cliente es obligatorio")
@Size(max = 100)
@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Nombre inválido")
String nombreCliente,

@NotBlank(message = "El nombre de la mascota es obligatorio")
@Size(max = 100)
@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "Nombre de la mascota inválido")
String nombreMascota,

@NotBlank(message = "La razón de la cita es obligatoria")
@Size(max = 500)
String razonCita,

@NotNull(message = "La fecha es obligatoria")
@FutureOrPresent(message = "La fecha debe ser presente o futura")
LocalDateTime fechaCita,

EstadoCita estadoCita

) {}
