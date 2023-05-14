package net.slonka.greencode.transactions.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
    public String debitAccount;
    public String creditAccount;
    public BigDecimal amount;

    public Transaction() {
        this.amount = new BigDecimal("0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(debitAccount, that.debitAccount) && Objects.equals(creditAccount, that.creditAccount) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debitAccount, creditAccount, amount);
    }
}
