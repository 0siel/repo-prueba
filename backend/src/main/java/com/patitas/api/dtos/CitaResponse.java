package com.patitas.api.dtos;


import java.time.LocalDateTime;
import com.patitas.api.enums.EstadoCita;

public record CitaResponse(
    Long id,
    String nombreCliente,
    String nombreMascota,
    String razonCita,
    LocalDateTime fechaCita,
    EstadoCita estadoCita
    
) {}
