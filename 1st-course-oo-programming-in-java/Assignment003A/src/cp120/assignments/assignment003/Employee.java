package cp120.assignments.assignment003;

public class Employee {

    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Company employer;

    public Employee(String fName, String lName, String em, String pNumber, Company co){
        this.firstName = fName;
        this.lastName = lName;
        this.email = em;
        this.phoneNumber = pNumber;
        this.employer = co;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Company getEmployer() {
        return employer;
    }
}
