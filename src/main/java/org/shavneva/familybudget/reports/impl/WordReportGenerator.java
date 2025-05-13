package org.shavneva.familybudget.reports.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.reports.impl.docsGenerator.WordGenerator;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class WordReportGenerator implements ReportGenerator {

    private final TransactionService transactionService;
    private final WordGenerator wordGenerator;
    private final BalanceService balanceService;

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    @Override
    public String getFileExtension() {
        return "docx";
    }

    @Override
    public byte[] generateReport(String username, String date, String currency) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);
        Map<String, Double> balances = balanceService.calculateBalances(transactions, currency);
        return wordGenerator.generateReport(transactions, balances);
    }
}
