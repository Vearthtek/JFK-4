package entity;

/**
 * Created by Vearthtek on 2017-06-13.
 */
public class Entity {
    private String firstName;
    private String lastName;
    private Double salary;
    private String email;

    public Entity(String firstName, String lastName, Double salary, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.email = email;
    }

    public String[] toArrayString() {
        return new String[] {firstName, lastName, salary.toString(), email};
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}