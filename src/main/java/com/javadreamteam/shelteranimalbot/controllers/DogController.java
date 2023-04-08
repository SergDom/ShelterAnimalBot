package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.Dog;
import com.javadreamteam.shelteranimalbot.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("dogs")
public class DogController {
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    private final DogService dogService;

    @Operation(
            summary = "Создание собаки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные создаваемой собаки." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданной собаки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Dog> createCustomer(@RequestBody Dog Dog) {
        return ResponseEntity.ok(dogService.create(Dog));
    }

    @Operation(
            summary = "Изменение данных собаки.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные о собаке с изменениями. Все поля обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Dog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененные данные о собаке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Собака с данным id не найдена"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Dog> updateDog(@RequestBody Dog Dog) {
        Dog updatedDog = dogService.update(Dog);
        if (updatedDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDog);
    }

    @Operation(
            summary = "Удаление собаки по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Союака успешно удалена"
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Dog> deleteDog(
            @Parameter(description = "id удаляемого владельца животного")
            @PathVariable long id) {
        dogService.removeById(id);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Поиск владельца животного по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденой собаке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "собака с данным id не найден"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Dog> findDog(
            @Parameter(description = "Идентификатор собаки", example = "1")
            @PathVariable Long id) {

        Dog Dog = dogService.getById(id);
        if (Dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Dog);
    }

    @Operation(
            summary = "Получение всех союак из БД.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные собаки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Dog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "собаки не найдены"
                    )
            }
    )
    @GetMapping("/find-all-dogs")
    public ResponseEntity<Collection<Dog>> getDogsList() {
        return ResponseEntity.ok(dogService.getAll());
    }
}
