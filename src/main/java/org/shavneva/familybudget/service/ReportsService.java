package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;

import org.shavneva.familybudget.service.impl.TransactionService;
import org.shavneva.familybudget.service.impl.WordGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReportsService {

    private final TransactionService transactionService;
    private final WordGenerator wordGenerator;
    private final BalanceService balanceService;

    public byte[] generateWordReportForUser(String username, String date, String currency) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);
        Map<String, Double> balances = balanceService.calculateBalances(transactions, currency);
        return wordGenerator.generateReport(transactions, balances);
    }

}
