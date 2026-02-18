package com.patitas.api.service;

import java.util.List;

import com.patitas.api.dtos.CitaRequest;
import com.patitas.api.dtos.CitaResponse;
import com.patitas.api.enums.EstadoCita;

public interface CitaServiceInt {
    
    List<CitaResponse> listAll();
    
    CitaResponse getById(Long id);
   
    CitaResponse register(CitaRequest request);
   
    CitaResponse update(CitaRequest request, Long id);
    
    CitaResponse updateStatus(Long id, EstadoCita nuevoEstado);
    
    void delete(Long id);
    
}
