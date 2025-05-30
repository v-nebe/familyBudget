package org.shavneva.familybudget.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

public interface ICurrencyController {
    @GetMapping("/rates")
    Map<String, Double> getRate();
}
