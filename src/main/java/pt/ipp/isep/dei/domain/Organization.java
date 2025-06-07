package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.*;
import pt.ipp.isep.dei.repository.CityRepository;
import pt.ipp.isep.dei.repository.CreateMapRepository;
import pt.ipp.isep.dei.repository.IndustryRepository;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String vatNumber;
    private String name;
    private String website;
    private String phone;

    private final CreateMapRepository mapRepository;
    private final CityRepository cityRepository;
    private final IndustryRepository industryRepository;
    private final StationRepository stationRepository;

    private final List<Employee> employees = new ArrayList<>();

    public Organization(String vatNumber,
                        CreateMapRepository mapRepository,
                        CityRepository cityRepository,
                        IndustryRepository industryRepository,
                        StationRepository stationRepository) {

        if (vatNumber == null || vatNumber.isEmpty())
            throw new IllegalArgumentException("VAT number cannot be null or empty.");

        this.vatNumber = vatNumber;
        this.mapRepository = mapRepository;
        this.cityRepository = cityRepository;
        this.industryRepository = industryRepository;
        this.stationRepository = stationRepository;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public CreateMapRepository getMapRepository() {
        return mapRepository;
    }

    public CityRepository getCityRepository() {
        return cityRepository;
    }

    public IndustryRepository getIndustryRepository() {
        return industryRepository;
    }

    public StationRepository getStationRepository() {
        return stationRepository;
    }

    private final List<Map> maps = new ArrayList<>();

    // Adicione estes métodos à classe Organization
    public List<Map> getMaps() {
        return Collections.unmodifiableList(maps);
    }

    public void addMap(Map map) {
        if (map != null && !maps.contains(map)) {
            maps.add(map);
            mapRepository.save(map); // Se necessário persistir no repositório
        }
    }

    public void addEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Employee cannot be null");
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }


    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public Organization copy() {
        Organization copy = new Organization(this.vatNumber,
                this.mapRepository,
                this.cityRepository,
                this.industryRepository,
                this.stationRepository);
        copy.name = this.name;
        copy.website = this.website;
        copy.phone = this.phone;

        for (Employee e : this.employees) {
            copy.addEmployee(e); // Se Employee for mutável, usar e.clone()
        }

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return vatNumber.equals(that.vatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vatNumber);
    }

    @Override
    public String toString() {
        return String.format("Organization: %s (VAT: %s)", name, vatNumber);
    }

    public boolean anyEmployeeHasEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        for (Employee employee : employees) {
            if (email.equalsIgnoreCase(employee.getEmail())) {
                return true;
            }
        }
        return false;
    }
    public Employee findEmployeeByEmail(String email) {
        for (Employee e : this.getEmployees()) { // <- CORRETO
            if (e.getEmail().equalsIgnoreCase(email)) {
                return e;
            }
        }
        return null;
    }


}
