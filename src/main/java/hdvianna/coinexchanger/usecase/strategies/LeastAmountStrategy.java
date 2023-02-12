package hdvianna.coinexchanger.usecase.strategies;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.exceptions.UnableToProcessChange;
import hdvianna.coinexchanger.usecase.results.ChangeStrategyResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;
import java.util.Comparator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class LeastAmountStrategy implements ChangeStrategy {
    @Override
    public ChangeStrategyResult change(List<Amount<Bill>> billAmounts, State<Coin> coinState) throws UnableToProcessChange {
        final var coinStateMap = coinState.getAmounts().stream().collect(toMap(
                amount -> amount.getMoney().getValue(),
                Amount::getCount
        ));
        final var coinResultMap = coinState.getAmounts().stream().collect(toMap(
                amount -> amount.getMoney().getValue(),
                amount -> 0
        ));
        final var coinValues = coinStateMap.keySet().stream().sorted(byDesc()).collect(toList());

        processChange(billAmounts, coinValues, coinStateMap, coinResultMap);

        final var stateAmount = coinStateMap.keySet().stream()
                .map(value -> Amount.of(coinStateMap.get(value), Coin.of(value)))
                .toList();
        final var resultAmount = coinResultMap.keySet().stream()
                .map(value -> Amount.of(coinResultMap.get(value), Coin.of(value)))
                .toList();
        return ChangeStrategyResult.of(State.of(stateAmount), resultAmount);
    }

    private void processChange(List<Amount<Bill>> billAmounts,
                               List<Integer> coinValues,
                               Map<Integer, Integer> coinStateMap,
                               Map<Integer, Integer> coinResultMap) throws UnableToProcessChange {
        for (Amount<Bill> amount : billAmounts) {
            var currentBillAmount = amount.getCount() * amount.getMoney().getValue() * 100;
            for(int coinValue : coinValues) {
                final var maxNumberOfCoins = currentBillAmount / coinValue;
                final var currentCoinStateCount = coinStateMap.get(coinValue);
                final var currentCoinResultCount = coinResultMap.get(coinValue);
                final var usedCoins = Math.min(maxNumberOfCoins, currentCoinStateCount);
                currentBillAmount -= usedCoins * coinValue;
                coinStateMap.replace(coinValue, currentCoinStateCount - usedCoins);
                coinResultMap.replace(coinValue, currentCoinResultCount + usedCoins);
            }
            if (currentBillAmount > 0) {
                throw new UnableToProcessChange();
            }
        }
    }
    private static Comparator<Integer> byDesc() {
        return (valueLeft, valueRight) -> {
            if (valueLeft < valueRight) {
                return 1;
            } else if(valueLeft.equals(valueRight)) {
                return 0;
            }
            return -1;
        };
    }

}
