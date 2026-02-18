package com.patitas.auth.config;


import com.patitas.auth.enums.RolNombre;
import com.patitas.auth.model.Usuario;
import com.patitas.auth.repository.RolRepository;
import com.patitas.auth.repository.UsuarioRepository;
import com.patitas.auth.model.Rol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(RolNombre.ROLE_ADMIN));
                rolRepository.save(new Rol(RolNombre.ROLE_STAFF));
            }

            
            if (usuarioRepository.count() == 0) {
                Rol adminRol = rolRepository.findByRolNombre(RolNombre.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Rol Admin no encontrado."));

                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setEmail("admin@patitas.com");
                
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRol));

                usuarioRepository.save(admin);
                System.out.println("Administrador por defecto creado");
            }
        };
    }
}
