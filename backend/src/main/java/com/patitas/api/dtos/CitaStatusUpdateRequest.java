package com.patitas.api.dtos;

import com.patitas.api.enums.EstadoCita;
import jakarta.validation.constraints.NotNull;

public record CitaStatusUpdateRequest(
        @NotNull(message = "El nuevo estado es obligatorio")
        EstadoCita estadoCita
) {
}