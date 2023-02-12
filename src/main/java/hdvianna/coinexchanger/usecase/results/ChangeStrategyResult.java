package hdvianna.coinexchanger.usecase.results;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Coin;
import hdvianna.coinexchanger.model.State;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;


@Value
@RequiredArgsConstructor(staticName="of")
public class ChangeStrategyResult {
    State<Coin> resultingState;
    List<Amount<Coin>> changeResult;
}
