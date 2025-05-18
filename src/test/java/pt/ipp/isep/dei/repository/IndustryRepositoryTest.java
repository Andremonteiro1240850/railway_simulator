package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.IndustryRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class IndustryRepositoryTest {

    @Test
    public void testAddIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        Optional<Industry> result = repository.add(industry);

        assertTrue(result.isPresent());
        assertEquals(industry, result.get());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddDuplicateIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        repository.add(industry);
        Optional<Industry> duplicateResult = repository.add(industry);

        assertFalse(duplicateResult.isPresent());
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void testAddNullIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Optional<Industry> result = repository.add(null);

        assertFalse(result.isPresent());
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    public void testContainsIndustry() {
        IndustryRepository repository = new IndustryRepository();
        Employee employee = new Employee("test@email.com");
        Map map = new Map("Test Map", 10, 10, employee);
        Industry industry = new Industry(map, 5, 5, employee);

        repository.add(industry);

        assertTrue(repository.contains(industry));
    }
}