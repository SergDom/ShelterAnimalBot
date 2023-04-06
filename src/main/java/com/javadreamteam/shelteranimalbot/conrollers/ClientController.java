package com.javadreamteam.shelteranimalbot.conrollers;
import com.javadreamteam.shelteranimalbot.model.Client;
import com.javadreamteam.shelteranimalbot.service.ClientService;
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
public class ClientController {
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    private final ClientService clientService;

    @Operation(
            summary = "Создание владельца животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные создаваемого владельца животного." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного владельца животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )

                    )
            }
    )
    @PostMapping
    public ResponseEntity<Client> createCustomer(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.create(client));
    }

    @Operation(
            summary = "Изменение данных владельца животного.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные о владельце животного с изменениями. Все поля обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Client.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененные данные о владельце животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Владелец животного с данным id не найден"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {

        Client updatedClient = clientService.update(client);
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
    @DeleteMapping("{ClientId}")
    public ResponseEntity<Client> deleteClient(
            @Parameter(description = "id удаляемого владельца животного")
            @PathVariable long clientId) {

        clientService.removeById(clientId);
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
                                    schema = @Schema(implementation = Client.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "владелец животного с данным id не найден"
                    )
            }
    )
    @GetMapping("{ClientId}")
    public ResponseEntity<Client> findClient(
            @Parameter(description = "Идентификатор владельца животного", example = "1")
            @PathVariable Long ClientId) {

        Client client = clientService.getById(ClientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }
    @GetMapping("/find-all-records")
    public ResponseEntity getClientsList() {
        return ResponseEntity.ok(clientService.getAll());
    }
    @GetMapping("/find--client-by-chatId")
    public ResponseEntity getClientByName(@RequestParam Long chatId) {
        return ResponseEntity.ok(clientService.getByChatId(chatId));
    }
}
