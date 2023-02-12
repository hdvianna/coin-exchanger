package hdvianna.coinexchanger.usecase;

import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.exceptions.UnableToProcessChange;
import hdvianna.coinexchanger.usecase.requests.ChangeRequest;
import hdvianna.coinexchanger.usecase.results.ChangeResult;
import hdvianna.coinexchanger.usecase.results.ChangeStrategyResult;
import hdvianna.coinexchanger.usecase.specifications.AndOfEntityListSpecification;
import hdvianna.coinexchanger.usecase.strategies.ChangeStrategy;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Stack;

import static hdvianna.coinexchanger.usecase.statuses.ChangeResultStatus.*;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class DefaultCoinExchangerTest {
    @Test
    public void invalidBills() {
        final var billAmountListSpecification = (AndOfEntityListSpecification<List<Bill>, Bill>) mock(AndOfEntityListSpecification.class);
        when(billAmountListSpecification.isSpecifiedBy(any())).thenReturn(false);
        final var coinExchanger = new DefaultCoinExchanger(mock(Stack.class), mock(ChangeStrategy.class), billAmountListSpecification);
        final var result = coinExchanger.exchange(ChangeRequest.of(mock(List.class)));
        assertEquals(ChangeResult.of(INVALID_BILLS, emptyList()), result);
    }
    @Test
    public void unableToChange() {
        assertDoesNotThrow(() -> {
            final var billAmountListSpecification = (AndOfEntityListSpecification<List<Bill>, Bill>) mock(AndOfEntityListSpecification.class);
            when(billAmountListSpecification.isSpecifiedBy(any())).thenReturn(true);

            final var changeStrategy = mock(ChangeStrategy.class);
            when(changeStrategy.change(any(), any())).thenThrow(UnableToProcessChange.class);

            final var states = mock(Stack.class);
            when(states.peek()).thenReturn( State.of(emptyList()));

            final var coinExchanger = new DefaultCoinExchanger(states, changeStrategy, billAmountListSpecification);
            final var result = coinExchanger.exchange(ChangeRequest.of(mock(List.class)));
            assertEquals(ChangeResult.of(UNABLE_TO_CHANGE, emptyList()), result);
        });
    }

    @Test
    public void success() {
        assertDoesNotThrow(() -> {
            final var billAmountListSpecification = (AndOfEntityListSpecification<List<Bill>, Bill>) mock(AndOfEntityListSpecification.class);
            when(billAmountListSpecification.isSpecifiedBy(any())).thenReturn(true);

            final var changeStrategy = mock(ChangeStrategy.class);
            final var coinAmounts = mock(List.class);
            when(changeStrategy.change(any(), any())).thenReturn(ChangeStrategyResult.of(State.of(emptyList()), coinAmounts));

            final var states = mock(Stack.class);
            when(states.peek()).thenReturn( State.of(emptyList()));

            final var coinExchanger = new DefaultCoinExchanger(states, changeStrategy, billAmountListSpecification);
            final var result = coinExchanger.exchange(ChangeRequest.of(mock(List.class)));
            assertEquals(ChangeResult.of(SUCCESS, coinAmounts), result);
        });
    }
}
