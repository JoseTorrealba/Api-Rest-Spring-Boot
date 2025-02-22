package cl.orquideaexpress.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom-health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Orquidea Express API");
        health.put("timestamp", System.currentTimeMillis());
        
        // Aquí puedes agregar más verificaciones específicas
        // Por ejemplo, verificar conexión a base de datos, servicios externos, etc.
        
        return ResponseEntity.ok(health);
    }
} 