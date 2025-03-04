package org.shavneva.familybudget.controller;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.TableService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/word")
@AllArgsConstructor
public class TableController {
    private final TableService tableService;
    private final TransactionService transactionService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadWordFile(){
        List<Transaction> transactions = transactionService.read();
        byte[] wordFile = tableService.generateWordFile(transactions);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = transactions.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(wordFile);

    }
}
