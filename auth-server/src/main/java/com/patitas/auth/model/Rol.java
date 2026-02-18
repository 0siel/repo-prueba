package com.patitas.auth.model;

import com.patitas.auth.enums.RolNombre;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_nombre", nullable = false, unique = true)
    private RolNombre rolNombre;
    
    public Rol(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
