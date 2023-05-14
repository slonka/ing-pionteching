package net.slonka.greencode.transactions.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    public String account;
    public int debitCount;
    public int creditCount;
    public BigDecimal balance;

    public Account() {
        this.balance = new BigDecimal("0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account1 = (Account) o;
        return debitCount == account1.debitCount && creditCount == account1.creditCount && Objects.equals(account, account1.account) && Objects.equals(balance, account1.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, debitCount, creditCount, balance);
    }
}
