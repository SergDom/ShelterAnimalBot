package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.Volunteer;
import com.javadreamteam.shelteranimalbot.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Добавление нового волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавлен новый волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "волонтер не добавлен"
                    )
            }
    )
    @PostMapping
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.createVolunteer(volunteer);
    }

    @Operation(
            summary = "Поиск волонтера по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден волонтер с параметрами",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "волонтер не найден"
                    )
            }
    )
    @GetMapping("/{volunteerId}")
    public ResponseEntity<Volunteer> readVolunteer(@PathVariable long volunteerId) {
        Volunteer findVolunteer = volunteerService.getById(volunteerId);
        if (findVolunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findVolunteer);
    }

    @Operation(
            summary = "Редактирование волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновленный волонтер",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "волонтер не найден"
                    )
            }
    )
    @PutMapping("/{volunteerId}")
    public Volunteer updateVolunteer(@PathVariable long volunteerId,
                                     @RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteerId, volunteer);
    }

}
