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
        cita.setFechaCita(request.fechaCita());
        
        return cita;
    }

    public CitaResponse toResponse(Cita entity) {
    	if(entity == null) return null;
    	
        return new CitaResponse(
            entity.getId(),
            entity.getNombreCliente(),
            entity.getNombreMascota(),
            entity.getRazonCita(),
            entity.getFechaCita(),
            entity.getEstadoCita()
        );
    }

    public void updateEntity(Cita entity, CitaRequest request) {
        
        entity.setNombreCliente(request.nombreCliente());
        entity.setNombreMascota(request.nombreMascota());
        entity.setRazonCita(request.razonCita());
        entity.setFechaCita(request.fechaCita());
        
        if (request.estadoCita() != null) {
            entity.setEstadoCita(request.estadoCita());
        }
    }
}
