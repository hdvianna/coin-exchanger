package hdvianna.coinexchanger.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName="of")
public class Coin implements Money {
    int value;
}
