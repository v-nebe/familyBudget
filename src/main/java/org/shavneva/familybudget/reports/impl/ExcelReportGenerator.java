package org.shavneva.familybudget.reports.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.reports.ReportGenerator;
import org.shavneva.familybudget.reports.impl.docsGenerator.ExcelGenerator;
import org.shavneva.familybudget.service.impl.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ExcelReportGenerator implements ReportGenerator {
    private final TransactionService transactionService;
    private final ExcelGenerator excelGenerator;
    private final BalanceService balanceService;

    @Override
    public MediaType getSupportedMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public byte[] generateReport(String username, String date, String currency) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);
        Map<String, Double> balances = balanceService.calculateBalances(transactions, currency);
        return excelGenerator.generateReport(transactions, balances);
    }
}
