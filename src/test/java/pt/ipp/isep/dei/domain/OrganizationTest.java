package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.repository.*;

class OrganizationTest {
    private Organization organization;
    private CreateMapRepository mapRepository;
    private CityRepository cityRepository;
    private IndustryRepository industryRepository;
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        mapRepository = Repositories.getInstance().getMapRepository();
        cityRepository = Repositories.getInstance().getCityRepository();
        industryRepository = Repositories.getInstance().getIndustryRepository();
        stationRepository = Repositories.getInstance().getStationRepository();
        organization = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
    }

    @Test
    void testConstructorWithValidParameters() {
        assertNotNull(organization);
        assertEquals("123456789", organization.getVatNumber());
    }

    @Test
    void testConstructorWithNullVatNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            new Organization(null, mapRepository, cityRepository, industryRepository, stationRepository)
        );
    }

    @Test
    void testConstructorWithEmptyVatNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            new Organization("", mapRepository, cityRepository, industryRepository, stationRepository)
        );
    }

    @Test
    void testSetName() {
        organization.setName("Test Organization");
        assertEquals("Test Organization", organization.getName());
    }

    @Test
    void testSetWebsite() {
        organization.setWebsite("www.test.com");
        assertEquals("www.test.com", organization.getWebsite());
    }

    @Test
    void testSetPhone() {
        organization.setPhone("123456789");
        assertEquals("123456789", organization.getPhone());
    }

    @Test
    void testGetVatNumber() {
        assertEquals("123456789", organization.getVatNumber());
    }

    @Test
    void testGetName() {
        organization.setName("Test Organization");
        assertEquals("Test Organization", organization.getName());
    }

    @Test
    void testGetWebsite() {
        organization.setWebsite("www.test.com");
        assertEquals("www.test.com", organization.getWebsite());
    }

    @Test
    void testGetPhone() {
        organization.setPhone("123456789");
        assertEquals("123456789", organization.getPhone());
    }

    @Test
    void testEquals() {
        Organization sameOrg = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        Organization differentOrg = new Organization("987654321", mapRepository, cityRepository, industryRepository, stationRepository);
        
        assertEquals(organization, sameOrg);
        assertNotEquals(organization, differentOrg);
    }

    @Test
    void testHashCode() {
        Organization sameOrg = new Organization("123456789", mapRepository, cityRepository, industryRepository, stationRepository);
        assertEquals(organization.hashCode(), sameOrg.hashCode());
    }
} 