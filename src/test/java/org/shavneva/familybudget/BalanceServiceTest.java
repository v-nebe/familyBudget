package org.shavneva.familybudget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shavneva.familybudget.entity.Transaction;
import org.shavneva.familybudget.service.impl.BalanceService;
import org.shavneva.familybudget.service.impl.CurrencyService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    BalanceService balanceService;

    private List<Transaction> transactions;

    @BeforeEach
    void setUp(){
        transactions = List.of(
                new Transaction("BYN", "100"),
                new Transaction("USD", "50"),
                new Transaction("EUR", "30"),
                new Transaction("BYN", "200"),
                new Transaction("USD", "20")
        );
    }

    @Test
    void testCalculateBalancesWithoutConversion() {
        Map<String, Double> balances = balanceService.calculateBalances(transactions, null);

        assertEquals(300.0, balances.get("BYN"));
        assertEquals(70.0, balances.get("USD"));
        assertEquals(30.0, balances.get("EUR"));
    }

    @Test
    void testCalculateBalancesWithConversionToBYN() {
        when(currencyService.getExchangesRates()).thenReturn(Map.of(
                "USD_BYN", 3.0,
                "EUR_BYN", 3.5
        ));

        Map<String, Double> convertedBalances = balanceService.calculateBalances(transactions, "BYN");

        double expectedBalance = 300 + (70 * 3.0) + (30 * 3.5);
        assertEquals(expectedBalance, convertedBalances.get("BYN"));
    }

    @Test
    void testCalculateBalancesWithConversionToUSD() {
        when(currencyService.getExchangesRates()).thenReturn(Map.of(
                "BYN_USD", 0.33,
                "EUR_USD", 1.1
        ));

        Map<String, Double> convertedBalances = balanceService.calculateBalances(transactions, "USD");

        double expectedBalance = (300 * 0.33) + 70 + (30 * 1.1);
        assertEquals(expectedBalance, convertedBalances.get("USD"));
    }
}
