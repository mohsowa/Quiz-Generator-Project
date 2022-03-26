import java.io.Serializable;

abstract public class User implements Serializable {
    public void user() {

    }

    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String getType();
    public abstract String getLastName();
}

class Student extends User implements Serializable{
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    Student(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getType(){
        return this.getClass().getName();
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }
}

class Instructor extends User implements Serializable{
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    Instructor(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getType(){
        return this.getClass().getName();
    }
    @Override
    public String getLastName() {
        return this.lastName;
    }
}
