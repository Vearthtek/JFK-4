package entity;

import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by Vearthtek on 2017-06-15.
 */
public class EntityJS extends ScriptableObject {
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
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.email = email;
    }

    public void jsConstructor() {
        this.firstName = "";
        this.lastName = "";
        this.salary = 0.;
        this.email = "";
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
