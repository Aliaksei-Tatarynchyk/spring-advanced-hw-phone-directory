package com.epam.phone.directory.model;

import java.util.Currency;
import java.util.Locale;

public class AmountOfMoney {

    double value;
    Currency currency = Currency.getInstance(Locale.US);

    public AmountOfMoney(double value) {
        this.value = value;
    }

    public AmountOfMoney(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
