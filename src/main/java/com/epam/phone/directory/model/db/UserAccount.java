package com.epam.phone.directory.model.db;

import java.util.Currency;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.epam.phone.directory.model.AmountOfMoney;
import com.epam.phone.directory.model.exception.InsufficientFundsException;
import com.epam.phone.directory.model.exception.ProhibitedFundsOperationException;

@Entity
public class UserAccount {

    public static final Currency DEFAULT_ACCOUNT_CURRENCY = Currency.getInstance(Locale.US);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    public Long id;

    double accountBalance;

    public UserAccount() {
    }

    private UserAccount(Builder builder) {
        id = builder.id;
        accountBalance = builder.accountBalance;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getRemainingAccountBalance() {
        return accountBalance + " " + DEFAULT_ACCOUNT_CURRENCY.getSymbol();
    }

    public boolean increaseBalance(AmountOfMoney amountOfMoney) throws ProhibitedFundsOperationException {
        if (amountOfMoney.getValue() < 0) {
            throw new ProhibitedFundsOperationException("Can't increase the balance because the amount of money is negative");
        }
        double conversionRate = takeConversionRate(amountOfMoney);
        accountBalance += conversionRate * amountOfMoney.getValue();
        return true;
    }

    public boolean decreaseBalance(AmountOfMoney amountOfMoney) throws InsufficientFundsException, ProhibitedFundsOperationException {
        if (amountOfMoney.getValue() < 0) {
            throw new ProhibitedFundsOperationException("Can't decrease the balance because the amount of money is negative");
        }
        double conversionRate = takeConversionRate(amountOfMoney);
        double amountToDeduct = conversionRate * amountOfMoney.getValue();
        if (accountBalance < amountToDeduct) {
            throw new InsufficientFundsException("There is not enough money on account balance to perform the operation");
        }
        accountBalance -= amountToDeduct;
        return true;
    }

    private double takeConversionRate(AmountOfMoney amountOfMoney) {
        if (DEFAULT_ACCOUNT_CURRENCY.equals(amountOfMoney.getCurrency())) {
            return 1;
        } else {
            return amountOfMoney.getCurrency().getNumericCode() / 2.0;
        }
    }

    public static final class Builder {
        private Long id;
        private double accountBalance;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withAccountBalance(double accountBalance) {
            this.accountBalance = accountBalance;
            return this;
        }

        public UserAccount build() {
            return new UserAccount(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
