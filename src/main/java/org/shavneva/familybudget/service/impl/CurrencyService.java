package org.shavneva.familybudget.service.impl;

import org.json.JSONObject;
import org.shavneva.familybudget.service.ICurrencyService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService implements ICurrencyService {
    private final String API_URL = "https://api.nbrb.by/exrates/rates/";

    @Override
    public Map<String, Double> getExchangesRates(String... currencies) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Double> rates = new HashMap<>();

        for (String currency : currencies) {
            String url = API_URL + currency + "?parammode=2";
            try {
                String response = restTemplate.getForObject(url, String.class);
                JSONObject jsonObject = new JSONObject(response);
                rates.put(currency, jsonObject.getDouble("Cur_OfficialRate"));
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при получении курса валюты " + currency, e);
            }
        }

        if (rates.containsKey("USD") && rates.containsKey("EUR") &&
                rates.get("USD") > 0 && rates.get("EUR") > 0) {
            rates.put("USD/EUR", rates.get("USD") / rates.get("EUR"));
            rates.put("EUR/USD", rates.get("EUR") / rates.get("USD"));
        }

        return rates;
    }
}
