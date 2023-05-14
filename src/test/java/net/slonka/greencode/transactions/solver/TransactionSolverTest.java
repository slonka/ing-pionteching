package net.slonka.greencode.transactions.solver;

import net.slonka.greencode.transactions.domain.Account;
import net.slonka.greencode.transactions.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionSolverTest {

    @Test
    void testSingleTransaction() {
        Transaction[] transactions = new Transaction[]{
                new Transaction("32309111922661937852684864", "06105023389842834748547303", new BigDecimal("10.90"))
        };

        List<Account> result = TransactionSolver.processTransactions(transactions);

        Account expectedResult1 = new Account();
        expectedResult1.account = "06105023389842834748547303";
        expectedResult1.creditCount = 1;
        expectedResult1.balance = new BigDecimal("10.90");

        Account expectedResult2 = new Account();
        expectedResult2.account = "32309111922661937852684864";
        expectedResult2.debitCount = 1;
        expectedResult2.balance = new BigDecimal("-10.90");

        assertEquals(2, result.size());
        assertEquals(expectedResult1, result.get(0));
        assertEquals(expectedResult2, result.get(1));
    }

    @Test
    void testMultipleTransactions() {
        Transaction[] transactions = new Transaction[]{
                new Transaction("32309111922661937852684864", "06105023389842834748547303", new BigDecimal("10.90")),
                new Transaction("31074318698137062235845814", "66105036543749403346524547", new BigDecimal("200.90")),
                new Transaction("66105036543749403346524547", "32309111922661937852684864", new BigDecimal("50.10"))
        };

        List<Account> result = TransactionSolver.processTransactions(transactions);

        Account expectedResult1 = new Account();
        expectedResult1.account = "06105023389842834748547303";
        expectedResult1.creditCount = 1;
        expectedResult1.balance = new BigDecimal("10.90");

        Account expectedResult2 = new Account();
        expectedResult2.account = "31074318698137062235845814";
        expectedResult2.debitCount = 1;
        expectedResult2.balance = new BigDecimal("-200.90");

        Account expectedResult3 = new Account();
        expectedResult3.account = "32309111922661937852684864";
        expectedResult3.debitCount = 1;
        expectedResult3.creditCount = 1;
        expectedResult3.balance = new BigDecimal("39.20");

        Account expectedResult4 = new Account();
        expectedResult4.account = "66105036543749403346524547";
        expectedResult4.debitCount = 1;
        expectedResult4.creditCount = 1;
        expectedResult4.balance = new BigDecimal("150.80");

        assertEquals(4, result.size());
        assertEquals(expectedResult1, result.get(0));
        assertEquals(expectedResult2, result.get(1));
        assertEquals(expectedResult3, result.get(2));
        assertEquals(expectedResult4, result.get(3));
    }
}