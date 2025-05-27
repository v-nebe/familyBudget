package org.shavneva.familybudget.service;

import java.util.Map;

public interface IConverterService {
    Map<String, Double> convertBalances(Map<String, Double> balances, String targetCurrency);
}
