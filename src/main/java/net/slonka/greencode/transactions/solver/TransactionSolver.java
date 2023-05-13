package net.slonka.greencode.transactions.solver;

import net.slonka.greencode.transactions.domain.Account;
import net.slonka.greencode.transactions.domain.Transaction;

import java.util.*;

public class TransactionSolver {

    public static List<Account> processTransactions(Transaction[] transactions) {
        Map<String, Account> accounts = new HashMap<>();

        for (Transaction transaction : transactions) {
            Account debitAccount = accounts.getOrDefault(transaction.debitAccount, new Account());
            debitAccount.account = transaction.debitAccount;
            debitAccount.debitCount++;
            debitAccount.balance -= transaction.amount;
            accounts.put(transaction.debitAccount, debitAccount);

            Account creditAccount = accounts.getOrDefault(transaction.creditAccount, new Account());
            creditAccount.account = transaction.creditAccount;
            creditAccount.creditCount++;
            creditAccount.balance += transaction.amount;
            accounts.put(transaction.creditAccount, creditAccount);
        }

        List<Account> result = new ArrayList<>(accounts.values());
        result.sort(Comparator.comparing(a -> a.account));

        return result;
    }
}
