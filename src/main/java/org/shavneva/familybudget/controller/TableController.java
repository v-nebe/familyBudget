package org.shavneva.familybudget.controller;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.TableService;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/word")
@AllArgsConstructor
public class TableController {
    private final TableService tableService;
    private final TransactionService transactionService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadWordFile(@RequestParam String nickname, @RequestParam String date){

        List<Transaction> transactions = transactionService.getTransactionsByUser(nickname, date);

        byte[] wordFile = tableService.generateWordFile(transactions);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = transactions.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(wordFile);

    }
}
