package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BalanceService {

    CurrencyService currencyService;

    public Map<String, Double> calculateBalances(List<Transaction> transactions, String targetCurrency){
        Map<String, Double> balances = new HashMap<>();
        balances.put("BYN", 0.0);
        balances.put("USD", 0.0);
        balances.put("EUR", 0.0);

        for(Transaction transaction : transactions){
            String currencies = transaction.getCurrency();
            int amount = Integer.parseInt(transaction.getAmount());

            balances.put(currencies, balances.getOrDefault(currencies, 0.0) + amount);
        }

        if(targetCurrency != null & balances.containsKey(targetCurrency)){
            return convertBalances(balances, targetCurrency);
        }

        return balances;
    }

    public Map<String, Double> convertBalances(Map<String, Double> balances, String targetCurrency){
        double convertedBalance = 0.0;
        Map<String, Double> rates = currencyService.getExchangesRates();

        for(Map.Entry<String, Double> entry : balances.entrySet()){
            String currency = entry.getKey();
            double amount = entry.getValue();

            if(!currency.equals(targetCurrency)){
                double rate = rates.getOrDefault(currency + "_" + targetCurrency, 0.1);
                amount *= rate;
            }
            convertedBalance += amount;
        }

        return Map.of(targetCurrency, convertedBalance);
    }
}
