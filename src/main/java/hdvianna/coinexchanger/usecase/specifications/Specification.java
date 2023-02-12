package hdvianna.coinexchanger.usecase.specifications;

public interface Specification<V extends Object> {
    public boolean isSpecifiedBy(V value);
}
