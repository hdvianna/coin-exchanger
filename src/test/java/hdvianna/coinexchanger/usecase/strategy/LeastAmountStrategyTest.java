package hdvianna.coinexchanger.usecase.strategy;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.exceptions.UnableToProcessChange;
import hdvianna.coinexchanger.usecase.strategies.LeastAmountStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class LeastAmountStrategyTest {

    private final LeastAmountStrategy leastAmountStrategy = new LeastAmountStrategy();

    @ParameterizedTest(name = "{0}")
    @MethodSource("providedSuccessfulCases")
    public void changeSuccessful(String testName, List<Amount<Bill>> billAmounts, State<Coin> inputState, List<Amount<Coin>> coinAmount, State<Coin> expectedState) {
        assertDoesNotThrow(() -> {
            final var result = leastAmountStrategy.change(billAmounts, inputState);
            assertEquals(result.getChangeResult(), coinAmount);
            assertEquals(result.getResultingState(), expectedState);
        });
    }

    private static Stream<Arguments> providedSuccessfulCases() {
        return Stream.of(
                Arguments.of(
                        "Given 1 bill of 1 and state is 2 coins of 0.5 when changing coins then result is 2 coins of 0.5 and state is 0 coins of 0.5 ",
                        newArrayList(makeBillAmount(1, 1)),
                        State.of(newArrayList(makeCoinAmount(50, 2))),
                        newArrayList(makeCoinAmount(50, 2)),
                        State.of(newArrayList(makeCoinAmount(50, 0)))
                ),
                Arguments.of(
                        "Given 2 bills of 1 and state is 2 coins of 0.5 and 10 coins of 0.10 when changing coins then result is 2 coins of 0.5 and 10 coins of 0.10 and state is 0 coins of 0.50 and 0 coins o .10",
                        newArrayList(makeBillAmount(1, 2)),
                        State.of(newArrayList(makeCoinAmount(10, 10), makeCoinAmount(50, 2))),
                        newArrayList(makeCoinAmount(50, 2), makeCoinAmount(10, 10)),
                        State.of(newArrayList(makeCoinAmount(50, 0), makeCoinAmount(10, 0)))
                ),
                Arguments.of(
                        "Given 2 bills of 10 and state is 20 coins of 0.5 and 100 coins of 0.10 when changing coins then result is 20 coins of 0.5 and 100 coins of 0.10 and state is 0 coins of 0.50 and 0 coins o .10",
                        newArrayList(makeBillAmount(10, 2)),
                        State.of(newArrayList(makeCoinAmount(50, 20),makeCoinAmount(10, 100))),
                        newArrayList(makeCoinAmount(50, 20), makeCoinAmount(10, 100)),
                        State.of(newArrayList(makeCoinAmount(50, 0), makeCoinAmount(10, 0)))
                ),
                Arguments.of(
                        "Given 4 bills of 5 and 2 bills of 5 and state is 20 coins of 0.5 and 100 coins of 0.10 when changing coins then result is 20 coins of 0.5 and 100 coins of 0.10 and state is 0 coins of 0.50 and 0 coins o .10",
                        newArrayList(makeBillAmount(5, 4)),
                        State.of(newArrayList(makeCoinAmount(50, 20),makeCoinAmount(10, 100))),
                        newArrayList(makeCoinAmount(50, 20), makeCoinAmount(10, 100)),
                        State.of(newArrayList(makeCoinAmount(50, 0), makeCoinAmount(10, 0)))
                ),
                Arguments.of(
                        "Given 1 bill of 10 and 2 bills of 5 and state is 20 coins of 0.5 and 100 coins of 0.10 when changing coins then result is 20 coins of 0.5 and 100 coins of 0.10 and state is 0 coins of 0.50 and 0 coins o .10",
                        newArrayList(makeBillAmount(10, 1), makeBillAmount(5, 2)),
                        State.of(newArrayList(makeCoinAmount(50, 20),makeCoinAmount(10, 100))),
                        newArrayList(makeCoinAmount(50, 20), makeCoinAmount(10, 100)),
                        State.of(newArrayList(makeCoinAmount(50, 0), makeCoinAmount(10, 0)))
                ),
                Arguments.of(
                        "Given 1 bill of 10 and 2 bills of 5 and state is 40 coins of 0.5 and 100 coins of 0.10 when changing coins then result is 40 coins of 0.5 and 0 coins of 0.10 and state is 0 coins of 0.50 and 100 coins o .10",
                        newArrayList(makeBillAmount(10, 1), makeBillAmount(5, 2)),
                        State.of(newArrayList(makeCoinAmount(50, 40),makeCoinAmount(10, 100))),
                        newArrayList(makeCoinAmount(50, 40), makeCoinAmount(10, 0)),
                        State.of(newArrayList(makeCoinAmount(50, 0), makeCoinAmount(10, 100)))
                )
        );

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("providedNotSuccessfulCases")
    public void changeNotSuccessful(String testName, List<Amount<Bill>> billAmounts, State<Coin> inputState) {
        assertThrows(UnableToProcessChange.class, () -> {
            leastAmountStrategy.change(billAmounts, inputState);
        });
    }

    private static Stream<Arguments> providedNotSuccessfulCases() {
        return Stream.of(
                Arguments.of(
                        "Given 1 bill of 1 and state is 1 coins of 0.5 when changing coins then throws UnableToProcessException",
                        newArrayList(makeBillAmount(1, 1)),
                        State.of(newArrayList(makeCoinAmount(50, 1)))
                )
        );

    }

    private static Amount<Bill> makeBillAmount(int billValue, int billCount) {
        return Amount.of(billCount, Bill.of(billValue));
    }

    private static Amount<Coin> makeCoinAmount(int coinValue, int coinCount) {
        return Amount.of(coinCount, Coin.of(coinValue));
    }


}
