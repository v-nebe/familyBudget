package org.shavneva.familybudget.controller;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/currency")
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/rates")
    public Map<String, Double> getRate(){
        return currencyService.getExchangesRates("USD", "EUR");

    }

}
