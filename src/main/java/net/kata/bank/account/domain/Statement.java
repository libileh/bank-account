package net.kata.bank.account.domain;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Statement {

    public static final String OPERATION_HEADER = "|OPERATION TYPE |DATE         |AMOUNT";
    private List<Operation> operations;

    private Statement(List<Operation> operations) {
        this.operations = operations;
    }

    public static Statement createEmptyStatement() {
        return new Statement(new ArrayList<>());
    }

    public static Statement createStatementWithOperations(List<Operation> operations) {
        if (operations.isEmpty())
            throw new IllegalArgumentException("initial operations must not be empty");
        return new Statement(operations);
    }


    public void registerDeposit(BigDecimal amount, LocalDate date) {
        Operation operation = new Operation.OperationBuilder()
                .withOperationType(OperationType.DEPOSIT)
                .withDate(date)
                .withAmount(amount)
                .build();
        operations.add(operation);
    }

    public void registerWithdraw(BigDecimal amount, LocalDate date) {
        Operation operation = new Operation.OperationBuilder()
                .withAmount(amount)
                .withDate(date)
                .withOperationType(OperationType.WITHDRAW)
                .build();
        operations.add(operation);
    }

    public void print(PrintStream printStream) {
        printStream.println(OPERATION_HEADER);
        operations.stream()
                .map(Operation::operationToPrint)
                .forEach(printStream::println);
    }

    public List<Operation> getOperations() {
        return operations;
    }
}