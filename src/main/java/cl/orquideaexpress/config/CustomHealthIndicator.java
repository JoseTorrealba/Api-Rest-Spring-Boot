package cl.orquideaexpress.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("service", "Orquidea Express API");
        details.put("status", "Running");
        details.put("timestamp", LocalDateTime.now().toString());
        details.put("version", "1.0.0");
        
        // Puedes agregar más información del sistema
        details.put("memory", Runtime.getRuntime().freeMemory());
        details.put("processors", Runtime.getRuntime().availableProcessors());
        details.put("maxMemory", Runtime.getRuntime().maxMemory());

        try {
            // Aquí puedes agregar verificaciones adicionales
            return Health.up()
                    .withDetails(details)
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withException(e)
                    .withDetails(details)
                    .build();
        }
    }
} 