package com.patitas.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patitas.api.enums.EstadoCita;
import com.patitas.api.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
	
	boolean existsByFechaCitaAndEstadoCita(LocalDateTime fechaCita, EstadoCita estadoCita);
	
	List<Cita> findByNombreClienteContainingIgnoreCaseOrNombreMascotaContainingIgnoreCase(
            String nombreCliente, 
            String nombreMascota
    );
}
