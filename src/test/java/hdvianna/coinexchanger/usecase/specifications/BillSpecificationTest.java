package hdvianna.coinexchanger.usecase.specifications;

import hdvianna.coinexchanger.model.Amount;
import hdvianna.coinexchanger.model.Bill;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class BillSpecificationTest {

    @Test
    public void billValueIsValid() {
        final var billSpecification = new BillSpecification(List.of(Bill.of(10)));
        final var result = billSpecification.isSpecifiedBy(Bill.of(10));
        assertTrue(result);
    }

    @Test
    public void billValueIsNotValid() {
        final var billSpecification = new BillSpecification(emptyList());
        final var result = billSpecification.isSpecifiedBy(Bill.of(10));
        assertFalse(result);
    }
}
