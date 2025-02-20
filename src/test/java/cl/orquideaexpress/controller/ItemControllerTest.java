package cl.orquideaexpress.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import cl.orquideaexpress.entity.Item;
import cl.orquideaexpress.service.ItemRestService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemRestService itemRestService;


    @InjectMocks
    private ItemController itemController;

    private WebTestClient webTestClient;

    private Item mockItem;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
            .bindToController(itemController)
            .build();

        mockItem = new Item();
        mockItem.setId("1");
        mockItem.setBody("Test Item");
    }

    @Test
    void getAllItems_ShouldReturnItems() {
        when(itemRestService.getAllItems(anyInt(), anyInt()))
            .thenReturn(Flux.just(mockItem));

        webTestClient.get()
            .uri("/api/v1/items?page=0&size=10")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Item.class)
            .hasSize(1)
            .contains(mockItem);
    }

    @Test
    void getItem_WhenExists_ShouldReturnItem() {
        when(itemRestService.getItem(anyString()))
            .thenReturn(Mono.just(mockItem));

        webTestClient.get()
            .uri("/api/v1/items/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Item.class)
            .isEqualTo(mockItem);
    }

    @Test
    void getItem_WhenNotExists_ShouldReturn404() {
        when(itemRestService.getItem(anyString()))
            .thenReturn(Mono.empty());

        webTestClient.get()
            .uri("/api/v1/items/999")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void createItem_ShouldReturnCreatedItem() {
        when(itemRestService.createItem(any(Item.class)))
            .thenReturn(Mono.just(mockItem));

        webTestClient.post()
            .uri("/api/v1/items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mockItem)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Item.class)
            .isEqualTo(mockItem);
    }

    @Test
    void updateItem_WhenExists_ShouldReturnUpdatedItem() {
        when(itemRestService.updateItem(anyString(), any(Item.class)))
            .thenReturn(Mono.just(mockItem));

        webTestClient.put()
            .uri("/api/v1/items/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mockItem)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Item.class)
            .isEqualTo(mockItem);
    }

    @Test
    void updateItem_WhenNotExists_ShouldReturn404() {
        when(itemRestService.updateItem(anyString(), any(Item.class)))
            .thenReturn(Mono.empty());

        webTestClient.put()
            .uri("/api/v1/items/999")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(mockItem)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void deleteItem_ShouldReturnNoContent() {
        when(itemRestService.deleteItem(anyString()))
            .thenReturn(Mono.empty());

        webTestClient.delete()
            .uri("/api/v1/items/1")
            .exchange()
            .expectStatus().isNoContent();

        verify(itemRestService).deleteItem("1");
    }
}