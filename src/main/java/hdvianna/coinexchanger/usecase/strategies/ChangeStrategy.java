package hdvianna.coinexchanger.usecase.strategies;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.exceptions.UnableToProcessChange;
import hdvianna.coinexchanger.usecase.results.ChangeStrategyResult;

import java.util.List;

public interface ChangeStrategy {
    public ChangeStrategyResult change(List<Amount<Bill>> billAmounts, State<Coin> coinState) throws UnableToProcessChange;
}
