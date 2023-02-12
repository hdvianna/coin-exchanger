package hdvianna.coinexchanger.usecase;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import hdvianna.coinexchanger.usecase.exceptions.UnableToProcessChange;
import hdvianna.coinexchanger.usecase.requests.ChangeRequest;
import hdvianna.coinexchanger.usecase.results.ChangeResult;
import hdvianna.coinexchanger.usecase.specifications.AndOfEntityListSpecification;
import hdvianna.coinexchanger.usecase.strategies.ChangeStrategy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Stack;

import static hdvianna.coinexchanger.usecase.statuses.ChangeResultStatus.*;
import static java.util.Collections.emptyList;

@RequiredArgsConstructor
public class DefaultCoinExchanger implements CoinExchanger {
    private final Stack<State<Coin>> states;
    private final ChangeStrategy changeStrategy;
    private final AndOfEntityListSpecification<List<Bill>, Bill> billAmountListSpecification;

    @Override
    public ChangeResult exchange(ChangeRequest changeRequest) {
        final var bills = extractBills(changeRequest);
        if (billAmountListSpecification.isSpecifiedBy(bills)) {
            try {
                synchronized (states) {
                    final var changeStrategyResult = changeStrategy.change(changeRequest.getBillAmounts(), states.peek());
                    states.push(changeStrategyResult.getResultingState());
                    return ChangeResult.of(SUCCESS, changeStrategyResult.getChangeResult());
                }
            } catch (UnableToProcessChange unableToProcessChange){
                return ChangeResult.of(UNABLE_TO_CHANGE, emptyList());
            }
        }
        return ChangeResult.of(INVALID_BILLS, emptyList());
    }

    private List<Bill> extractBills(ChangeRequest changeRequest) {
        return changeRequest
                .getBillAmounts()
                .stream()
                .map(Amount::getMoney)
                .toList();
    }
}
