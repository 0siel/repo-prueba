package com.patitas.api.controller;

import com.patitas.api.dtos.CitaRequest;
import com.patitas.api.dtos.CitaResponse;
import com.patitas.api.dtos.CitaStatusUpdateRequest;
import com.patitas.api.service.CitaServiceInt;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaServiceInt citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponse>> listAll() {
        return ResponseEntity.ok(citaService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CitaResponse> register(@Valid @RequestBody CitaRequest request) {
        return new ResponseEntity<>(citaService.register(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponse> update(@Valid @RequestBody CitaRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(citaService.update(request, id));
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaResponse> updateStatus(
            @PathVariable Long id, 
            @Valid @RequestBody CitaStatusUpdateRequest request) {
        
        return ResponseEntity.ok(citaService.updateStatus(id, request.estadoCita()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
