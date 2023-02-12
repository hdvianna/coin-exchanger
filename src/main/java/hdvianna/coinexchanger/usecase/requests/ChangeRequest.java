package hdvianna.coinexchanger.usecase.requests;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor(staticName = "of")
public class ChangeRequest {
    List<Amount<Bill>> billAmounts;
}
