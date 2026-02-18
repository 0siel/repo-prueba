package com.patitas.auth.repository;

import com.patitas.auth.enums.RolNombre;
import com.patitas.auth.model.Rol;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
