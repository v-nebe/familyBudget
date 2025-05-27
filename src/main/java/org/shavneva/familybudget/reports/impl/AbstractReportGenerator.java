package org.shavneva.familybudget.reports.impl;

import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.reports.ReportFormat;
import org.shavneva.familybudget.service.BalanceService;
import org.shavneva.familybudget.service.impl.TransactionService;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public abstract class AbstractReportGenerator implements ReportFormat {
    protected final TransactionService transactionService;
    protected final BalanceService balanceService;

    protected AbstractReportGenerator(TransactionService transactionService, BalanceService balanceService) {
        this.transactionService = transactionService;
        this.balanceService = balanceService;
    }

    public byte[] prepareReportData(String username, String date, String currency) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(username, date);

        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст, невозможно создать отчет.");
        }

        Map<String, Double> balances = balanceService.calculateBalances(transactions, currency);
        String month = new SimpleDateFormat("yyyy-MM").format(transactions.get(0).getDate());

        ReportData data = new ReportData(transactions, balances, month);
        return generateReport(data);
    }

    protected abstract byte[] generateReport(ReportData data);
}

