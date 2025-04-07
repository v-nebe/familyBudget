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

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadWordFile(@RequestParam String nickname, @RequestParam String date,
                                                   @RequestParam String currency){

        byte[] reportData = reportsService.generateWordReportForUser(nickname, date, currency);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = transactions.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(reportData);

    }
}
