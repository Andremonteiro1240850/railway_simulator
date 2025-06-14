package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.enums.IndustryType;

import java.util.Arrays;
import java.util.List;

/**
 * Repository that provides access to available industry types.
 * Operates over the {@link IndustryType} enum.
 */
public class IndustryTypeRepository {

    /**
     * Retrieves all defined industry types.
     *
     * @return a list of all industry types
     */
    public List<IndustryType> getAllTypes() {
        return Arrays.asList(IndustryType.values());
    }

    /**
     * Checks whether the provided industry type is valid (i.e., not null).
     *
     * @param type the industry type to validate
     * @return true if the type is not null; false otherwise
     */
    public boolean isValidType(IndustryType type) {
        return type != null;
    }
}
