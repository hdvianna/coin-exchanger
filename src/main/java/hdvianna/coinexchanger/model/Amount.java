package hdvianna.coinexchanger.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName="of")
public class Amount<V extends Money> {
    int count;
    V money;
}
