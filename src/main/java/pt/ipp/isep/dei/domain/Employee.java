package pt.ipp.isep.dei.domain;

import java.util.Objects;

/**
 * Represents an employee in the system with an email address and optional organization association.
 */
public class Employee {
    private final String email;
    private String name;
    private String position;
    private String phone;
    private Organization organization;

    /**
     * Constructs a new Employee with the specified email.
     *
     * @param email the employee's email address
     * @throws IllegalArgumentException if email is null, empty, or invalid
     */
    public Employee(String email) {
        validateEmail(email);
        this.email = email;
    }

    /**
     * Constructs a new Employee with the specified email and organization.
     *
     * @param email the employee's email address
     * @param organization the organization the employee belongs to
     * @throws IllegalArgumentException if email is null, empty, or invalid
     */
    public Employee(String email, Organization organization) {
        validateEmail(email);
        this.email = email;
        this.organization = organization;
    }

    /**
     * Validates the email address format.
     *
     * @param email the email to validate
     * @throws IllegalArgumentException if email is null, empty, or invalid
     */
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Returns the employee's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the organization the employee belongs to.
     *
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Sets the organization for this employee.
     *
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * Checks if this employee has the specified email.
     *
     * @param email the email to check
     * @return true if the email matches this employee's email
     */
    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }

    /**
     * Creates a copy of this employee.
     *
     * @return a new Employee instance with the same email
     */
    public Employee clone() {
        return new Employee(this.email, this.organization);
    }

    /**
     * Compares this employee with another object for equality.
     * Two employees are considered equal if they have the same email address.
     *
     * @param o the object to compare with
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return email.equals(employee.email);
    }

    /**
     * Returns a hash code for this employee based on the email address.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Returns a string representation of this employee.
     *
     * @return a string containing the employee's email
     */
    @Override
    public String toString() {
        return "Employee [email=" + email + "]";
    }
}
