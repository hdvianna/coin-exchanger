package hdvianna.coinexchanger.usecase.specifications;

import hdvianna.coinexchanger.usecase.specifications.AndOfEntityListSpecification;
import hdvianna.coinexchanger.usecase.specifications.Specification;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AndOfEntityListSpecificationTest {

    @Test
    void allValidEntities() {
        final var specification = (Specification<Object>) mock(Specification.class);
        final AndOfEntityListSpecification<List<Object>,Object> andOfEntityListSpecification = new AndOfEntityListSpecification<>(specification);
        when(specification.isSpecifiedBy(any()))
                .thenReturn(true)
                .thenReturn(true);
        final var list = List.of(new Object(), new Object());
        final var result = andOfEntityListSpecification.isSpecifiedBy(list);
        assertTrue(result);
    }
    @Test
    void someValidEntities() {
        final var specification = (Specification<Object>) mock(Specification.class);
        final AndOfEntityListSpecification<List<Object>,Object> andOfEntityListSpecification = new AndOfEntityListSpecification<>(specification);
        when(specification.isSpecifiedBy(any()))
                .thenReturn(true)
                .thenReturn(false);
        final var list = List.of(new Object(), new Object());
        final var result = andOfEntityListSpecification.isSpecifiedBy(list);
        assertFalse(result);
    }

    @Test
    void emptyEntities() {
        final var specification = (Specification<Object>) mock(Specification.class);
        final AndOfEntityListSpecification<List<Object>,Object> andOfEntityListSpecification = new AndOfEntityListSpecification<>(specification);
        final var result = andOfEntityListSpecification.isSpecifiedBy(emptyList());
        assertTrue(result);
    }
}
