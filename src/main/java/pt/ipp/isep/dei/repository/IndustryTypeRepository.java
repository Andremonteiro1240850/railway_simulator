package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.enums.IndustryType;
import java.util.Arrays;
import java.util.List;

public class IndustryTypeRepository {
    public List<IndustryType> getAllTypes() {
        return Arrays.asList(IndustryType.values());
    }

    public boolean isValidType(IndustryType type) {
        return type != null;
    }
}