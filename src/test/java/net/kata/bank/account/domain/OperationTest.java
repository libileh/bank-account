package net.kata.bank.account.domain;

import net.kata.bank.account.exception.AmountBalanceException;
import net.kata.bank.account.exception.OperationTypeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createOperationWithoutType_thenShouldFail() {
        thrown.expect(OperationTypeException.class);
        thrown.expectMessage("Operation Type is required");
        new Operation.OperationBuilder()
                .withDate(LocalDate.now())
                .withAmount(BigDecimal.valueOf(1000D))
                .build();

    }

    @Test
    public void createOperationWithoutDate_thenShouldFail() {
        thrown.expect(DateTimeException.class);
        thrown.expectMessage("Date is required");
        new Operation.OperationBuilder()
                .withOperationType(OperationType.WITHDRAW)
                .withAmount(BigDecimal.valueOf(1000D))
                .build();

    }

    @Test
    public void createOperationWithoutAmount_thenShouldFail() {
        thrown.expect(AmountBalanceException.class);
        thrown.expectMessage("amount is required");
        new Operation.OperationBuilder()
                .withOperationType(OperationType.DEPOSIT)
                .withDate(LocalDate.now())
                .build();
    }

    @Test
    public void toPrintOperationType() {
        LocalDate now = LocalDate.now();
        Operation build = new Operation.OperationBuilder()
                .withDate(now)
                .withOperationType(OperationType.WITHDRAW)
                .withAmount(BigDecimal.valueOf(1000D))
                .build();
        assertThat(build.operationToPrint()).isEqualTo("|WITHDRAW      |" + now + "     |1000.0");
    }


}