package com.javadreamteam.shelteranimalbot.controllers;


import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.model.ClientCat;
import com.javadreamteam.shelteranimalbot.model.ClientDog;
import com.javadreamteam.shelteranimalbot.model.Dog;
import com.javadreamteam.shelteranimalbot.service.ClientDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientDogController {
    public ClientDogController(ClientDogService clientDogService) {
        this.clientDogService = clientDogService;
    }

    private final ClientDogService clientDogService;

    @Operation(
            summary = "Создание владельца животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные создаваемого владельца животного." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного владельца животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )

                    )
            }
    )
    @PostMapping
    public ResponseEntity<ClientDog> createCustomer(@RequestBody ClientDog clientDog, ClientStatus status) {
        return ResponseEntity.ok(clientDogService.create(clientDog, status));
    }

    @Operation(
            summary = "Изменение данных владельца животного.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные о владельце животного с изменениями. Все поля обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientDog.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененные данные о владельце животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец животного с данным id не найден"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<ClientDog> updateClient(@RequestBody ClientDog clientDog) {

        ClientDog updatedClient = clientDogService.update(clientDog);
        if (updatedClient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(
            summary = "Удаление владельца животного по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Владелец животного успешно удален"
                    )
            }
    )
    @DeleteMapping("{clientId}")
    public ResponseEntity<ClientDog> deleteClient(
            @Parameter(description = "id удаляемого владельца животного")
            @PathVariable long clientId) {

        clientDogService.removeById(clientId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Поиск владельца животного по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном владельце животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "владелец животного с данным id не найден"
                    )
            }
    )
    @GetMapping("{clientId}")
    public ResponseEntity<ClientDog> findClient(
            @Parameter(description = "Идентификатор владельца животного", example = "1")
            @PathVariable Long clientId) {

        ClientDog clientDog = clientDogService.getById(clientId);
        if (clientDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDog);
    }
    @Operation(
            summary = "Получение всех клиентов из БД.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные клиенты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "клиенты не найдены"
                    )
            }
    )

    @GetMapping("/find-all-records")
    public ResponseEntity getClientsList() {
        return ResponseEntity.ok(clientDogService.getAll());
    }

    @Operation(
            summary = "Получение всех клиентов из БД по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден клиент по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "клиент не найден"
                    )
            }
    )

    @GetMapping("/find-client-by-chatId")
    public ResponseEntity getClientById(@RequestParam Long chatId) {
        return ResponseEntity.ok(clientDogService.getByChatId(chatId));
    }

    @Operation(
            summary = "Увеличение дней испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный клиент",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Клиент не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class))
                    )
            })

    @PutMapping("/days/{id}")
    public ResponseEntity<ClientDog> changeDays(@Parameter(description = "Введите id клиента", example = "1")
                                                @PathVariable Long id,
                                                @Parameter(description = "1 - увеличить испытательный срок на 14 дней." +
                                                        "2  - увеличить испытательный срок на 30 дней", example = "1")
                                                @RequestParam Long numberDays) {
        ClientDog clientDog = clientDogService.changeNumberOfReportDays(id, numberDays);
        if (clientDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDog);
    }

    @Operation(
            summary = "Изменение статуса клиента",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный клиент",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Клиент не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class))
                    )
            }
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<ClientDog> updateStatus(@Parameter(description = "Введите id клиента", example = "1")
                                                  @PathVariable Long id,
                                                  @Parameter(description = "Выберете статус клиента", example = "IN_SEARCH")
                                                  @RequestParam (name = "Статус") ClientStatus status) {
        ClientDog clientDog = clientDogService.updateStatus(id, status);
        if (clientDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDog);
    }

    @Operation(
            summary = "Уведомление клиенту от волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомляемый клиент",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Клиент не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientDog.class))
                    )
            }
    )

    @PutMapping("/probation/{id}")
    public ResponseEntity<ClientDog> noticeToOwners(@Parameter(description = "Введите id клиента", example = "1")
                                                    @PathVariable Long id,
                                                    @Parameter(description = "1 - отчет плохо заполнен. " +
                                                            "2  - прошел испытальельный срок. 3 - не прошел испытательный срок",
                                                            example = "1")
                                                    @RequestParam Long number) {
        ClientDog clientDog = clientDogService.noticeToClient(id, number);
        if (clientDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientDog);
    }
}
