package net.kata.bank.account.domain;

import net.kata.bank.account.exception.AmountBalanceException;
import net.kata.bank.account.exception.OperationTypeException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Operation {

    private OperationType operationType;
    private LocalDate date;
    private BigDecimal amount;

    private Operation(OperationType operationType, LocalDate date,
                      BigDecimal amount) {
        this.operationType = operationType;
        this.date = date;
        this.amount = amount;
    }

    public String operationToPrint() {
        return String.format("|%s      |%s     |%s",
                operationType.toString(),
                date.format(DateTimeFormatter.ISO_DATE),
                amount);
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    static class OperationBuilder {
        private OperationType operationType;
        private LocalDate date;
        private BigDecimal amount;

        public OperationBuilder withOperationType(OperationType operation) {
            this.operationType = operation;
            return this;
        }

        public OperationBuilder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public OperationBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Operation build() {
            if (operationType == null)
                throw new OperationTypeException("Operation Type is required");
            if (date == null)
                throw new DateTimeException("Date is required");
            if (amount == null)
                throw new AmountBalanceException("amount is required");
            return new Operation(operationType, date, amount);
        }
    }
}