package hdvianna.coinexchanger.usecase.specifications;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class AndOfEntityListSpecification<L extends List<E>, E> implements Specification<L> {
    private final Specification<E> specification;
    @Override
    public boolean isSpecifiedBy(L entities) {
        return entities.stream().allMatch(entity -> specification.isSpecifiedBy((E) entity));
    }
}
