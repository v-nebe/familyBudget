package org.shavneva.familybudget.service;

import org.shavneva.familybudget.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceService {
    private int amountByn, amountUsd, amountEur;

    public Map<String, Integer> CalculateBalances(List<Transaction> transactions){
        Map<String, Integer> balances = new HashMap<>();
        balances.put("BYN", 0);
        balances.put("USD", 0);
        balances.put("EUR", 0);

        for(Transaction transaction : transactions){
           String currency = transaction.getCurrency();
           int amount = Integer.parseInt(transaction.getAmount());

           balances.put(currency, balances.getOrDefault(currency, 0) + amount);
        }

        return balances;
    }
}
