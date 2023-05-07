package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.ReportCat;
import com.javadreamteam.shelteranimalbot.model.ReportDog;
import com.javadreamteam.shelteranimalbot.service.ReportCatService;
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
 * работает с сущностью {@link ReportCatService}.
 */
@RestController
@RequestMapping("report_cat")
public class ReportCatController {
    private  final ReportCatService reportCatService;

    public ReportCatController(ReportCatService reportCatService) {
        this.reportCatService = reportCatService;
    }

    @Operation(
            summary = "Добавление отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    )
            }
    )

    @PostMapping
    public ReportCat createReport(@RequestBody ReportCat ReportCat){
        return reportCatService.createReport(ReportCat);
    }


    @Operation(
            summary = "Поиск отчета по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном отчете",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет с данным id не найден"
                    )
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<ReportCat> findReport(
            @Parameter(description = "Идентификатор отчета", example = "1")
            @PathVariable Long id) {

        ReportCat reportCat = reportCatService.findReportById(id);
        if (reportCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportCat);
    }


    @Operation(
            summary = "Редактирование отчета в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportCat.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден"
                    )
            }
    )

    @PutMapping
    public ResponseEntity<ReportCat> updateReport(@RequestBody ReportCat reportCat) {

        ReportCat updatedReportCat = reportCatService.updateReport(reportCat);
        if (updatedReportCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReportCat);
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
    public ResponseEntity<ReportCat> deleteReport(
            @Parameter(description = "Идентификатор отчета")
            @PathVariable long id) {
        reportCatService.deleteReport(id);

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
                                    array = @ArraySchema(schema = @Schema(implementation = ReportCat.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<ReportCat>> getAllReports() {
        return ResponseEntity.ok(reportCatService.getAllReports());
    }


}
