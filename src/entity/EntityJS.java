package entity;

import org.mozilla.javascript.NativeObject;

/**
 * Created by Vearthtek on 2017-06-15.
 */
public class EntityJS extends NativeObject {
    private String firstName;
    private String lastName;
    private Double salary;
    private String email;

    @Override
    public String getClassName() {
        return "Entity";
    }

    public EntityJS() {
    }

    public EntityJS(String firstName, String lastName, Double salary, String email) {
        jsConstructor(firstName, lastName, salary, email);
    }

    public void jsConstructor(String firstName, String lastName, Double salary, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.email = email;
        this.put("firstName", this, firstName);
        this.put("lastName", this, lastName);
        this.put("salary", this, salary);
        this.put("email", this, email);
    }

    public void jsSet_salary(Double value) {
        this.salary = value;
    }

    public Double jsGet_salary() {
        return this.salary;
    }

    public void jsSet_firstName(String value) {
        this.firstName = value;
    }

    public String jsGet_firstName() {
        return this.firstName;
    }

    public void jsSet_lastName(String value) {
        this.lastName = value;
    }

    public String jsGet_lastName() {
        return this.lastName;
    }

    public void jsSet_email(String value) {
        this.email = value;
    }

    public String jsGet_email() {
        return this.email;
    }
}
