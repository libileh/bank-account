package net.kata.bank.account.domain;

import net.kata.bank.account.exception.AmountBalanceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    private Account account;
    @Mock
    private PrintStream printer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        account = Account.createAccount(BigDecimal.valueOf(1000D));

    }

    @Test
    public void createAccount_withoutStatement() {
        assertThat(account.currentBalance()).isEqualTo(BigDecimal.valueOf(1000D));
    }

    @Test
    public void deposit() {
        assertThat(account.deposit(BigDecimal.valueOf(300D)).currentBalance()).isEqualTo(BigDecimal.valueOf(1300D));
    }

    @Test
    public void currentBalance() {
        assertThat(account.currentBalance()).isEqualTo(BigDecimal.valueOf(1000D));
    }

    @Test
    public void withdraw() {
        assertThat(account.withdraw(BigDecimal.valueOf(300D)).currentBalance()).isEqualTo(BigDecimal.valueOf(700D));
    }

    @Test
    public void withdrawal_amount_thenShouldFail() {
        thrown.expect(AmountBalanceException.class);
        thrown.expectMessage("Amount to withdraw must not be superior to the balance");
        account.withdraw(BigDecimal.valueOf(3000D)).currentBalance();
    }

    @Test
    public void printHistory() {
        Mockito.when(printer.toString()).thenReturn("|OPERATION TYPE |DATE|AMOUNT|DEPOSIT|2020-05-02|1000.0CURRENT BALANCE :   1000.0");
        account.printHistory(printer);
        assertThat(printer.toString())
                .isEqualToIgnoringWhitespace("|OPERATION TYPE |DATE|AMOUNT|DEPOSIT|2020-05-02|1000.0CURRENT BALANCE :   1000.0");
    }
}