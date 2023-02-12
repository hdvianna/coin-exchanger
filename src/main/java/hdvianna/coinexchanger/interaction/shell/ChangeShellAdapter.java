package hdvianna.coinexchanger.interaction.shell;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import hdvianna.coinexchanger.usecase.DefaultCoinExchangerInstance;
import hdvianna.coinexchanger.usecase.requests.ChangeRequest;
import hdvianna.coinexchanger.usecase.results.ChangeResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ChangeShellAdapter {

    public String change(int[] billsAndAmounts) {
        if (billsAndAmounts.length == 0) {
            return "No bills informed";
        }

        if (billsAndAmounts.length%2 > 0) {
            return "Bills and amounts do not match. Please check the absence of a bill or amount.";
        }

        final var changeRequest = makeChangeRequest(billsAndAmounts);
        final var coinExchanger = DefaultCoinExchangerInstance.get();
        final var result = coinExchanger.exchange(changeRequest);

        return switch (result.getStatus()){
            case INVALID_BILLS -> "Please check the bills entered. Some of them may not be valid.";
            case UNABLE_TO_CHANGE -> "There are not enough coins for changing. Please try again later.";
            case SUCCESS -> formatResult(result);
        };

    }
    private ChangeRequest makeChangeRequest(int[] billsAndAmounts) {
        final var amounts = new ArrayList<Amount<Bill>>();
        for(int i=0; i < billsAndAmounts.length; i+=2) {
            final var billValue = billsAndAmounts[i];
            final var billCount = billsAndAmounts[i+1];
            amounts.add(Amount.of(billCount, Bill.of(billValue)));
        }
        return ChangeRequest.of(amounts);
    }

    private String formatResult(ChangeResult result) {
        final var formattedAmounts = result.getCoinAmounts().stream().map(coinAmount ->
                String.format(
                    "\tCoin value = %s, amount = %s",
                        coinAmount.getMoney().getValue(),
                        coinAmount.getCount()
                )
        ).toList();
        return "Exchange Successful!\n" + formattedAmounts.stream().collect(Collectors.joining("\n"));
    }
}
