package cl.orquideaexpress.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.orquideaexpress.config.WebClientProperties;
import cl.orquideaexpress.entity.Item;
import cl.orquideaexpress.util.ApiException;
import cl.orquideaexpress.util.BadRequestException;
import cl.orquideaexpress.util.ResourceNotFoundException;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
// Custom exception classes


    @Service
    public class ClientRestService {
        private static final Logger log = LoggerFactory.getLogger(ClientRestService.class);
        private final WebClient webClient;
        private final WebClientProperties properties;

        public ClientRestService(WebClient webClient, WebClientProperties properties) {
             this.webClient = webClient;
             this.properties = properties;
        }

        public Flux<Item> getAllItems(int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/posts")
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .build())
                .retrieve()
                .bodyToFlux(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(this::handleError)
                .doOnError(error -> logError("Error fetching items", error));
    }

    // Get item by ID
    public Mono<Item> getItem(String id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(error -> {
                    if (error instanceof WebClientResponseException.NotFound) {
                        return new ResourceNotFoundException("Item not found with id: " + id);
                    }
                    return handleError(error);
                })
                .doOnError(error -> logError("Error fetching item with id: " + id, error));
    }

    // Create new item
    public Mono<Item> createItem(Item item) {
        return webClient.post()
                .uri("/posts")
                .bodyValue(item)
                .retrieve()
                .bodyToMono(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(error -> {
                    if (error instanceof WebClientResponseException.BadRequest) {
                        return new BadRequestException("Invalid item data: " + error.getMessage());
                    }
                    return handleError(error);
                })
                .doOnError(error -> logError("Error creating item", error));
    }

    // Update existing item
    public Mono<Item> updateItem(String id, Item item) {
        return webClient.put()
                .uri("/posts/{id}", id)
                .bodyValue(item)
                .retrieve()
                .bodyToMono(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(error -> {
                    if (error instanceof WebClientResponseException.NotFound) {
                        return new ResourceNotFoundException("Item not found with id: " + id);
                    }
                    if (error instanceof WebClientResponseException.BadRequest) {
                        return new BadRequestException("Invalid item data: " + error.getMessage());
                    }
                    return handleError(error);
                })
                .doOnError(error -> logError("Error updating item with id: " + id, error));
    }

    // Patch update item
    public Mono<Item> patchItem(String id, Map<String, Object> updates) {
        return webClient.patch()
                .uri("/posts/{id}", id)
                .bodyValue(updates)
                .retrieve()
                .bodyToMono(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(error -> {
                    if (error instanceof WebClientResponseException.NotFound) {
                        return new ResourceNotFoundException("Item not found with id: " + id);
                    }
                    if (error instanceof WebClientResponseException.BadRequest) {
                        return new BadRequestException("Invalid update data: " + error.getMessage());
                    }
                    return handleError(error);
                })
                .doOnError(error -> logError("Error patching item with id: " + id, error));
    }

    // Delete item
    public Mono<Void> deleteItem(String id) {
        return webClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(error -> {
                    if (error instanceof WebClientResponseException.NotFound) {
                        return new ResourceNotFoundException("Item not found with id: " + id);
                    }
                    return handleError(error);
                })
                .doOnError(error -> logError("Error deleting item with id: " + id, error));
    }

    // Bulk create items
    public Flux<Item> createItems(List<Item> items) {
        return Flux.fromIterable(items)
                .flatMap(this::createItem)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(this::handleError)
                .doOnError(error -> logError("Error in bulk item creation", error));
    }

    // Search items by criteria
    public Flux<Item> searchItems(Map<String, String> searchCriteria) {
        return webClient.get()
                .uri(uriBuilder -> {
                    searchCriteria.forEach(uriBuilder::queryParam);
                    return uriBuilder.path("/items/search").build();
                })
                .retrieve()
                .bodyToFlux(Item.class)
                .timeout(Duration.ofMillis(properties.getResponseTimeout()))
                .onErrorMap(this::handleError)
                .doOnError(error -> logError("Error searching items", error));
    }

    // Error handling helper methods
    private RuntimeException handleError(Throwable error) {
        if (error instanceof WebClientResponseException) {
            WebClientResponseException wcError = (WebClientResponseException) error;
            HttpStatus status = HttpStatus.valueOf(wcError.getStatusCode().value());
            
            switch (status) {
                case NOT_FOUND:
                    return new ResourceNotFoundException(wcError.getMessage());
                case BAD_REQUEST:
                    return new BadRequestException(wcError.getMessage());
                case UNAUTHORIZED:
                    return new ApiException("Unauthorized access", status);
                case FORBIDDEN:
                    return new ApiException("Access forbidden", status);
                case GATEWAY_TIMEOUT:
                case REQUEST_TIMEOUT:
                    return new ApiException("Request timeout", status);
                default:
                    return new ApiException("API Error: " + wcError.getMessage(), status);
            }
        }
        return new ApiException("Unexpected error: " + error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(String message, Throwable error) {
        if (error instanceof ApiException) {
            ApiException apiError = (ApiException) error;
            log.error("API Error - Status: {}, Message: {}, Details: {}", 
                apiError.getStatus(), message, error.getMessage());
        } else {
            log.error("Unexpected Error - Message: {}, Details: {}", 
                message, error.getMessage(), error);
        }
    }

}
