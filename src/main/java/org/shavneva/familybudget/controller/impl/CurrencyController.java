package org.shavneva.familybudget.controller.impl;

import lombok.AllArgsConstructor;
import org.shavneva.familybudget.controller.ICurrencyController;
import org.shavneva.familybudget.service.impl.CurrencyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/currency")
@AllArgsConstructor
public class CurrencyController implements ICurrencyController {

    private final CurrencyService currencyService;

    @Override
    public Map<String, Double> getRate(){
        return currencyService.getExchangesRates("USD", "EUR");

    }

}
