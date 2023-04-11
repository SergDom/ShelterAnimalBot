package com.javadreamteam.shelteranimalbot.controllers;

import com.javadreamteam.shelteranimalbot.model.Report;
import com.javadreamteam.shelteranimalbot.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Класс для обработки запросов от клиента и возвращения результатов,
 * работает с сущностью {@link ReportService}.
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    private  final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @Operation(
            summary = "Поиск отчета по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о найденном отчете",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет с данным id не найден"
                    )
            }
    )
    @GetMapping("{reportId}")
    public ResponseEntity<Report> findReport(
            @Parameter(description = "Идентификатор отчета", example = "1")
            @PathVariable long reportId) {

        Report report = reportService.findReportById(reportId);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
    @Operation(
            summary = "Получить список всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<Report>> findAllReports() {
        return ResponseEntity.ok(reportService.findAllReports());
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
    @GetMapping("{reportId}")
    public ResponseEntity<Report> deleteReport(
            @Parameter(description = "Идентификатор отчета")
            @PathVariable long reportId) {
        reportService.deleteReport(reportId);

        return ResponseEntity.ok().build();
    }

}
