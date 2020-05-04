package net.kata.bank.account.domain;


import net.kata.bank.account.exception.AmountBalanceException;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


public class Account {

    private Statement statement;
    private BigDecimal balance;

    private Account(Statement statement, BigDecimal balance) {
        this.statement = statement;
        this.balance = balance;
    }

    public static Account createAccount(BigDecimal initialBalance) {
        Statement statement = Statement.createEmptyStatement();
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            statement.registerDeposit(initialBalance, LocalDate.now());
        }
        return new Account(statement, initialBalance);
    }

    public Account deposit(BigDecimal amountToDeposit) {
        statement.registerDeposit(amountToDeposit, LocalDate.now());
        return setBalance(currentBalance().add(amountToDeposit));
    }

    public Account withdraw(BigDecimal amountToWithdraw) {
        validateWithdrawal(amountToWithdraw);
        statement.registerWithdraw(amountToWithdraw, LocalDate.now());
        return setBalance(currentBalance().subtract(amountToWithdraw));
    }

    public BigDecimal currentBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void printHistory(PrintStream printer) {
        statement.print(printer);
        printer.print(String.format("%s :   %s", "CURRENT BALANCE", currentBalance()));
    }

    private void validateWithdrawal(BigDecimal amountToWithdraw) {
        Optional.ofNullable(amountToWithdraw)
                .filter(amw-> this.currentBalance().compareTo(amw) > 0)
                .orElseThrow(()-> new AmountBalanceException("Amount to withdraw must not be superior to the balance"));
    }
}