package org.shavneva.familybudget.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ConverterService {
    CurrencyService currencyService;

    public Map<String, Double> convertBalances(Map<String, Double> balances, String targetCurrency) {
        double convertedBalance = 0.0;
        Map<String, Double> rates = currencyService.getExchangesRates("USD", "EUR");

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            String currency = entry.getKey();
            double amount = entry.getValue();

            if (currency.equals("BYN") && !currency.equals(targetCurrency)) {
                double rate = rates.getOrDefault(targetCurrency, 0.0);
                if (rate == 0.0) throw new RuntimeException("Курс BYN -> " + targetCurrency + " не найден");
                amount = amount / rate;
            }

            else if (!currency.equals("BYN") && targetCurrency.equals("BYN")) {
                double rate = rates.getOrDefault(currency, 0.0);
                if (rate == 0.0) throw new RuntimeException("Курс " + currency + " -> BYN не найден");
                amount = amount * rate;
            }

            else if (!currency.equals(targetCurrency)) {

                double fromRate = rates.getOrDefault(currency, 0.0);
                double toRate = rates.getOrDefault(targetCurrency, 0.0);
                if (fromRate == 0.0 || toRate == 0.0) {
                    throw new RuntimeException("Курс для " + currency + " или " + targetCurrency + " не найден");
                }
                double bynAmount = amount * fromRate;
                amount = bynAmount / toRate;
            }

            convertedBalance += amount;
        }

        return Map.of(targetCurrency, Math.round(convertedBalance * 10000d) / 10000d);
    }
}
