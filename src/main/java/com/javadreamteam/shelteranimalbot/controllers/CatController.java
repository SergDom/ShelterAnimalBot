package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.service.CatService;
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
@RequestMapping("cats")
public class CatController {
    private final CatService catService;
    public CatController(CatService catService) {
        this.catService = catService;
    }
    @Operation(
            summary = "Создание кошки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные создаваемой кошки." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданной кошки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Cat> createCustomer(@RequestBody Cat cat) {
        return ResponseEntity.ok(catService.create(cat));
    }

    @Operation(
            summary = "Изменение данных кошки.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные о кошке с изменениями. Все поля обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененные данные о кошке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "кошка с данным id не найдена"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Cat> updateCat(@RequestBody Cat cat) {
        Cat updatedCat = catService.update(cat);
        if (updatedCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCat);
    }

    @Operation(
            summary = "Удаление кошки по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "кошка успешно удалена"
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Cat> deleteCat(
            @Parameter(description = "id удаляемого владельца животного")
            @PathVariable long id) {
        catService.removeById(id);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Поиск владельца животного по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденой кошке",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "кошка с данным id не найден"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Cat> findCat(
            @Parameter(description = "Идентификатор кошки", example = "1")
            @PathVariable Long id) {

        Cat Cat = catService.getById(id);
        if (Cat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Cat);
    }

    @Operation(
            summary = "Получение всех собак из БД.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные кошки",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "кошки не найдены"
                    )
            }
    )
    @GetMapping("/find-all-cats")
    public ResponseEntity<Collection<Cat>> getCatsList() {
        return ResponseEntity.ok(catService.getAll());
    }
}
