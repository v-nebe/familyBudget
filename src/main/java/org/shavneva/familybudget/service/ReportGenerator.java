package org.shavneva.familybudget.service;

import org.shavneva.familybudget.entity.Transaction;

import java.util.List;

public interface ReportGenerator {
    byte[] generateReport(List<Transaction> transactionList);
}
