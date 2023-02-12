package hdvianna.coinexchanger.usecase.results;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.usecase.statuses.ChangeResultStatus;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor(staticName = "of")
public class ChangeResult {
    ChangeResultStatus status;
    List<Amount<Coin>> coinAmounts;
}
