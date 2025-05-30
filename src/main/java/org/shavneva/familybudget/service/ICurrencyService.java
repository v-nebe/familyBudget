package org.shavneva.familybudget.service;

import java.util.Map;

public interface ICurrencyService {
    Map<String, Double> getExchangesRates(String... currencies);
}
