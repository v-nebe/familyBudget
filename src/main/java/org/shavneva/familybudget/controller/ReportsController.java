package org.shavneva.familybudget.controller;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.ReportsService;
import org.shavneva.familybudget.service.impl.TransactionService;
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

        byte[] reportData = new byte[0];
        String filename = "";
        MediaType mediaType = null;

        if (acceptHeader.contains("application/pdf")){
            reportData = reportsService.generatePdfReportForUser(nickname, date, currency);
            filename = "report.pdf";
            mediaType = MediaType.APPLICATION_PDF;

        } else if (acceptHeader.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            reportData = reportsService.generateExcelReportForUser(nickname, date, currency);
            filename = "report.xlsx";
            mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        } else {
            reportData = reportsService.generateWordReportForUser(nickname, date, currency);
            filename = "report.docx";
            mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(mediaType)
                .body(reportData);

    }
}
