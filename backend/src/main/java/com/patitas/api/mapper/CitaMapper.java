package com.patitas.api.mapper;

import com.patitas.api.dtos.CitaRequest;
import com.patitas.api.dtos.CitaResponse;
import com.patitas.api.model.Cita;

import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequest request) {
    	if(request == null) return null;
        Cita cita = new Cita();
        
        cita.setNombreCliente(request.nombreCliente());
        cita.setNombreMascota(request.nombreMascota());
        cita.setRazonCita(request.razonCita());
        cita.setNumeroTelefono(request.numeroTelefono());
        cita.setFechaCita(request.fechaCita());
        cita.setEstadoCita(request.estadoCita());
        return cita;
    }

    public CitaResponse toResponse(Cita entity) {
    	if(entity == null) return null;
    	
        return new CitaResponse(
            entity.getId(),
            entity.getNombreCliente(),
            entity.getNombreMascota(),
            entity.getRazonCita(),
            entity.getNumeroTelefono(),
            entity.getFechaCita(),
            entity.getEstadoCita()
        );
    }

    public void updateEntity(Cita entity, CitaRequest request) {
        
        entity.setNombreCliente(request.nombreCliente());
        entity.setNombreMascota(request.nombreMascota());
        entity.setRazonCita(request.razonCita());
        entity.setNumeroTelefono(request.numeroTelefono());
        entity.setFechaCita(request.fechaCita());
        
        if (request.estadoCita() != null) {
            entity.setEstadoCita(request.estadoCita());
        }
    }
}
