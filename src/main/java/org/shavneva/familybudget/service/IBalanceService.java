package org.shavneva.familybudget.service;

import org.shavneva.familybudget.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface IBalanceService {
    Map<String, Double> calculateBalances(List<Transaction> transactions, String targetCurrency);
}
