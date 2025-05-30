package org.shavneva.familybudget.reports.impl;

import org.shavneva.familybudget.entity.Transaction;

import java.util.List;
import java.util.Map;

public record ReportData(List<Transaction> transactions,  Map<String, Double> balances, String month) {
}
