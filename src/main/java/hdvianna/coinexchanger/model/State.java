package hdvianna.coinexchanger.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor(staticName="of")
public class State<V extends Money> {
    List<Amount<V>> amounts;
}
