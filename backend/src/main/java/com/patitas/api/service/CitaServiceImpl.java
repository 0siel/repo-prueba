package com.patitas.api.service;

import com.patitas.api.dtos.CitaRequest;
import com.patitas.api.dtos.CitaResponse;
import com.patitas.api.enums.EstadoCita;
import com.patitas.api.exceptions.InvalidStateException;
import com.patitas.api.exceptions.ResourceNotFoundException;
import com.patitas.api.mapper.CitaMapper;
import com.patitas.api.model.Cita;
import com.patitas.api.repository.CitaRepository;
import com.patitas.api.service.CitaServiceInt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaServiceInt {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listAll() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponse getById(Long id) {
    	
        Cita cita = getCitaOrThrow(id);
        
        return citaMapper.toResponse(cita);
    }

    @Override
    @Transactional
    public CitaResponse register(CitaRequest request) {
        
        validarDisponibilidad(request.fechaCita());

        Cita nuevaCita = citaMapper.toEntity(request);
        
        nuevaCita.setEstadoCita(EstadoCita.PENDIENTE);
                
        Cita citaGuardada = citaRepository.save(nuevaCita);
        
        return citaMapper.toResponse(citaGuardada);
    }

    @Override
    @Transactional
    public CitaResponse update(CitaRequest request, Long id) {
        Cita citaExistente = getCitaOrThrow(id);

        if (!citaExistente.getFechaCita().equals(request.fechaCita())) {
            validarDisponibilidad(request.fechaCita());
        }

        citaMapper.updateEntity(citaExistente, request);
        
        Cita citaActualizada = citaRepository.save(citaExistente);
        return citaMapper.toResponse(citaActualizada);
    }

    @Override
    @Transactional
    public CitaResponse updateStatus(Long id, EstadoCita nuevoEstado) {
        Cita citaExistente = getCitaOrThrow(id);

        if (citaExistente.getEstadoCita() == nuevoEstado) {
            return citaMapper.toResponse(citaExistente);
        }

        validarTransicionEstado(citaExistente, nuevoEstado);

        citaExistente.setEstadoCita(nuevoEstado);
        
        Cita citaActualizada = citaRepository.save(citaExistente);
        return citaMapper.toResponse(citaActualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
    	
        getCitaOrThrow(id);
        
        citaRepository.deleteById(id);
    }
    
    private Cita getCitaOrThrow(Long id) {
    	return citaRepository.findById(id).orElseThrow(() -> 
    	new ResourceNotFoundException("No se encontró la cita con el ID: " + id ));
    }

    private void validarDisponibilidad(LocalDateTime fecha) {
        if (citaRepository.existsByFechaCitaAndEstadoCita(fecha, EstadoCita.PENDIENTE)) {
            throw new InvalidStateException("Ya existe una cita en estado PENDIENTE para la fecha y hora seleccionada.");
        }
    }

    private void validarTransicionEstado(Cita citaActual, EstadoCita nuevoEstado) {
        if (nuevoEstado == EstadoCita.ATENDIDA) {
            
            if (LocalDateTime.now().isBefore(citaActual.getFechaCita())) {
                throw new InvalidStateException("No se puede marcar la cita como ATENDIDA antes de su fecha programada.");
            }
        } 
        else if (nuevoEstado == EstadoCita.CANCELADA) {
            
            if (citaActual.getEstadoCita() == EstadoCita.ATENDIDA) {
                throw new InvalidStateException("No se puede cancelar una cita que ya ha sido finalizada (ATENDIDA).");
            }
        }
    }
}