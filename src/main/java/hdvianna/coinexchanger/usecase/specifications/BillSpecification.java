package hdvianna.coinexchanger.usecase.specifications;

import hdvianna.coinexchanger.model.Bill;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillSpecification implements Specification <Bill> {

    private final Map<Integer, Bill> billAmountMap;

    public BillSpecification(List<Bill> bills) {
        billAmountMap = bills.stream().collect(Collectors.toMap(
                Bill::getValue,
                bill -> bill)
        );
    }

    @Override
    public boolean isSpecifiedBy(Bill value) {
        return billAmountMap.containsKey(value.getValue());
    }
}
