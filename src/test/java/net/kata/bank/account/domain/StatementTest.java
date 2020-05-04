package net.kata.bank.account.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StatementTest {

    private Statement statement;
    @Mock
    private PrintStream printer;
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() {
        statement = Statement.createEmptyStatement();
    }

    @Test
    public void createStatementWithOperations() {
        LocalDate now = LocalDate.now();
        Operation build = new Operation.OperationBuilder()
                .withOperationType(OperationType.DEPOSIT)
                .withDate(now)
                .withAmount(BigDecimal.valueOf(1000D))
                .build();
        statement = Statement.createStatementWithOperations(Collections.singletonList(build));
        assertThat(statement.getOperations().get(0).getOperationType()).isEqualTo(build.getOperationType());
        assertThat(statement.getOperations().get(0).getDate()).isEqualTo(build.getDate());
        assertThat(statement.getOperations().get(0).getAmount()).isEqualTo(build.getAmount());
    }

    @Test
    public void registerDeposit() {
        LocalDate now = LocalDate.now();
        Operation build = new Operation.OperationBuilder()
                .withOperationType(OperationType.DEPOSIT)
                .withDate(now)
                .withAmount(BigDecimal.valueOf(1000D))
                .build();
        statement.registerDeposit(BigDecimal.valueOf(1000D), now);
        assertThat(statement.getOperations().get(0).getOperationType()).isEqualTo(build.getOperationType());
        assertThat(statement.getOperations().get(0).getDate()).isEqualTo(build.getDate());
        assertThat(statement.getOperations().get(0).getAmount()).isEqualTo(build.getAmount());
    }

    @Test
    public void registerWithdraw() {
        LocalDate now = LocalDate.now();
        Operation build = new Operation.OperationBuilder()
                .withOperationType(OperationType.WITHDRAW)
                .withDate(now)
                .withAmount(BigDecimal.valueOf(1000D))
                .build();
        statement.registerWithdraw(BigDecimal.valueOf(1000D), now);
        assertThat(statement.getOperations().get(0).getOperationType()).isEqualTo(build.getOperationType());
        assertThat(statement.getOperations().get(0).getDate()).isEqualTo(build.getDate());
        assertThat(statement.getOperations().get(0).getAmount()).isEqualTo(build.getAmount());
    }

    @Test
    public void createStatement_withOperation_thenShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("initial operations must not be empty");
        statement = Statement.createStatementWithOperations(Collections.emptyList());

    }
}