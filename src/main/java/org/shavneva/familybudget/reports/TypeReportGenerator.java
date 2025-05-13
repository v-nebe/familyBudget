package org.shavneva.familybudget.reports;

import org.shavneva.familybudget.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TypeReportGenerator {
    byte[] generateReport(List<Transaction> transactionList, Map<String, Double> balances);
}
