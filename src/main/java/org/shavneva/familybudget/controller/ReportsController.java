package org.shavneva.familybudget.controller;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.reports.ReportWrapper;
import org.shavneva.familybudget.service.ReportsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportsController {
    private final ReportsService reportsService;

    @GetMapping(value = "/download", produces = {
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    })
    public ResponseEntity<byte[]> downloadWordFile(@RequestParam String nickname,
                                                   @RequestParam String date,
                                                   @RequestParam String currency,
                                                   @RequestHeader("Accept") String acceptHeader){

       MediaType mediaType = MediaType.parseMediaType(acceptHeader);
       ReportWrapper report = reportsService.generateReport(nickname, date, currency, mediaType);
       String filename = "report." + report.fileExtension;

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(mediaType)
                .body(report.data);

    }
}
