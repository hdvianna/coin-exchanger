package hdvianna.coinexchanger.usecase;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.specifications.AndOfEntityListSpecification;
import hdvianna.coinexchanger.usecase.specifications.BillSpecification;
import hdvianna.coinexchanger.usecase.strategies.ChangeStrategy;
import hdvianna.coinexchanger.usecase.strategies.LeastAmountStrategy;

import java.util.List;
import java.util.Stack;

public class DefaultCoinExchangerInstance {
    private static CoinExchanger instance = null;

    public static CoinExchanger get() {
        if (instance == null) {
            instance = new DefaultCoinExchanger(
                    makeStates(),
                    makeChangeStrategy(),
                    makeBillListSpecification()
            );
        }
        return instance;
    }

    private static Stack<State<Coin>> makeStates() {
        final var oneCentCoinAmount = Amount.of(10000000, Coin.of(1));
        final var fiveCentsCoinAmount = Amount.of(5000000, Coin.of(5));
        final var tenCentsCoinAmount = Amount.of(1000000, Coin.of(10));
        final var twentyFiveCentsCoinAmount = Amount.of(750000, Coin.of(25));
        final var fiftyCentsCoinAmount = Amount.of(500000, Coin.of(50));
        final var initialState = State.of(List.of(
                oneCentCoinAmount, fiveCentsCoinAmount, tenCentsCoinAmount, fiftyCentsCoinAmount, twentyFiveCentsCoinAmount)
        );
        final var stack = (new Stack<State<Coin>>());
        stack.push(initialState);
        return stack;
    }

    private static ChangeStrategy makeChangeStrategy() {
        return new LeastAmountStrategy();
    }

    private static AndOfEntityListSpecification<List<Bill>, Bill> makeBillListSpecification() {
        final var oneMoneyBill = Bill.of(1);
        final var twoMoneyBill = Bill.of(2);
        final var fiveMoneyBill = Bill.of(5);
        final var tenMoneyBill = Bill.of(10);
        final var twentyMoneyBill = Bill.of(20);
        final var fifityMoneyBill = Bill.of(50);
        final var hundredMoneyBill = Bill.of(100);
        final var billSpecification = new BillSpecification(List.of(
                oneMoneyBill, twoMoneyBill, fiveMoneyBill, tenMoneyBill,
                twentyMoneyBill, fifityMoneyBill, hundredMoneyBill
        ));
        return new AndOfEntityListSpecification<>(billSpecification);
    }
}