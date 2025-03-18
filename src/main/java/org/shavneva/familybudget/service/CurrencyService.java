package org.shavneva.familybudget.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {

    private final String API_URl = "https://api.nbrb.by/exrates/rates/";

    public Map<String, Double> getExchangesRates(String... currencies){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Double> rates= new HashMap<>();

        for(String currency : currencies){
            String URL = API_URl + currency + "?parammode=2";
            try {
                String response = restTemplate.getForObject(URL, String.class);
                JSONObject jsonObject = new JSONObject(response);
                rates.put(currency, jsonObject.getDouble("Cur_OfficialRate"));
            }catch (Exception e){
                e.printStackTrace();
                rates.put(currency, 0.0);
            }
        }
        return rates;
    }

}
