package cl.orquideaexpress.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.orquideaexpress.entity.Item;
import cl.orquideaexpress.service.ItemRestService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ItemController {
    private final ItemRestService itemRestService;

    public ItemController(ItemRestService itemRestService) {
        this.itemRestService = itemRestService;
    }

    @GetMapping("/items")
    public Flux<Item> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return itemRestService.getAllItems(page, size);
    }

    @GetMapping("/items/{id}")
    public Mono<ResponseEntity<Item>> getItem(@PathVariable String id) {
        return itemRestService.getItem(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@Valid @RequestBody Item item) {
        return itemRestService.createItem(item);
    }

    @PutMapping("/items/{id}")
    public Mono<ResponseEntity<Item>> updateItem(
            @PathVariable String id,
            @Valid @RequestBody Item item) {
        return itemRestService.updateItem(id, item)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteItem(@PathVariable String id) {
        return itemRestService.deleteItem(id);
    }
}
