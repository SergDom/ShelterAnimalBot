package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.service.ReportDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Класс для обработки запросов от клиента и возвращения результатов,
 * работает с сущностью {@link ReportDogService}.
 */
@RestController
@RequestMapping("report")
public class ReportController {
    private  final ReportDogService reportDogService;

    public ReportController(ReportDogService reportDogService) {
        this.reportDogService = reportDogService;
    }

    @Operation(
            summary = "Добавление отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    )
            }
    )

    @PostMapping
    public ReportDog createReport(@RequestBody ReportDog reportDog){
        return reportDogService.createReport(reportDog);
    }


    @Operation(
            summary = "Поиск отчета по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном отчете",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет с данным id не найден"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<ReportDog> findReport(
            @Parameter(description = "Идентификатор отчета", example = "1")
            @PathVariable Long id) {

        ReportDog reportDog = reportDogService.findReportById(id);
        if (reportDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDog);
    }


    @Operation(
            summary = "Редактирование отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDog.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден"
                    )
            }
    )

    @PutMapping
    public ResponseEntity<ReportDog> updateReport(@RequestBody ReportDog reportDog) {

        ReportDog updatedReportDog = reportDogService.updateReport(reportDog);
        if (updatedReportDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReportDog);
    }

    @Operation(
            summary = "Удаление отчета по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет успешно удален"

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет с данным id не найден"
                    )
            }
    )

    @DeleteMapping("{id}")
    public ResponseEntity<ReportDog> deleteReport(
            @Parameter(description = "Идентификатор отчета")
            @PathVariable long id) {
        reportDogService.deleteReport(id);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDog.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<ReportDog>> getAllReports() {
        return ResponseEntity.ok(reportDogService.getAllReports());
    }


}
