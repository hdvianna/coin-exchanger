package hdvianna.coinexchanger.usecase;

import hdvianna.coinexchanger.usecase.requests.ChangeRequest;
import hdvianna.coinexchanger.usecase.results.ChangeResult;

public interface CoinExchanger {
    public ChangeResult exchange(ChangeRequest changeRequest);
}
