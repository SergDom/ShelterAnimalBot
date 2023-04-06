package com.javadreamteam.shelteranimalbot.conrollers;

`package com.javadreamteam.shelteranimalbot.conrollers;

import com.javadreamteam.shelteranimalbot.model.Adopter;
import com.javadreamteam.shelteranimalbot.service.ClientDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class AdopterController {
    public AdopterController(ClientDogService clientDogService) {
        this.clientDogService = clientDogService;
    }

    private final ClientDogService clientDogService;
    @GetMapping("/find-all-records")
    public ResponseEntity getClientsList() {
        return ResponseEntity.ok(clientDogService.getAll());
    }
    @GetMapping("/find--client-by-chatId")
    public ResponseEntity getClientByName(@RequestParam Long chatId) {
        return ResponseEntity.ok(clientDogService.getByChatId(chatId));
    }

    @Operation(
            summary = "Создание владельца животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные создаваемого владельца животного." +
                            "id переданный в теле будет игнорироваться, будет присвоен следующий id из БД. " +
                            "Все поля кроме id обязательны.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Adopter.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные созданного владельца животного",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adopter.class)
                            )

                    )
            }
    )
    @PostMapping
    public ResponseEntity<Adopter> createCustomer(@RequestBody Adopter adopter) {

        return ResponseEntity.ok(clientDogService.create(adopter));
    }


}
`