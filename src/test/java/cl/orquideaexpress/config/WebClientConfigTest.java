package cl.orquideaexpress.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class WebClientConfigTest {

    @Test
    void webClient_shouldReturnWebClientInstance() {
        // Arrange
        WebClientProperties properties = Mockito.mock(WebClientProperties.class);
        when(properties.getBaseUrl()).thenReturn("http://localhost:8080");
        when(properties.getConnectTimeout()).thenReturn(5000);
        when(properties.getResponseTimeout()).thenReturn(5000);
        when(properties.getReadTimeout()).thenReturn(5000);
        when(properties.getWriteTimeout()).thenReturn(5000);
        when(properties.getMaxInMemorySize()).thenReturn(16 * 1024 * 1024);

        WebClientConfig webClientConfig = new WebClientConfig();

        // Act
        WebClient webClient = webClientConfig.webClient(properties);

        // Assert
        assertNotNull(webClient);
    }
}