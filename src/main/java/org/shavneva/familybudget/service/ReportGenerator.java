package org.shavneva.familybudget.service;

import org.shavneva.familybudget.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface ReportGenerator {
    byte[] generateReport(List<Transaction> transactionList, Map<String, Double> balances);
}
