package com.patitas.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/api/hola")
	public Map<String, String> saludar(){
		Map<String, String> respuesta = new HashMap<>();
		
		respuesta.put("message", "Hello world desde el backend de Patitas 🐾");
		respuesta.put("status", "operacional");
		
		return respuesta;
	}

}
