package cp120.assignments.assignment003;

public class Employee {
	
	private String firstName;
	private String lastName;
	private static int numberOfEmployees;
	
	public Employee(String fName, String lName){
		this.firstName = fName;
		this.lastName = lName;
		setNumOfEmployees();
	}
	
	public void setNumOfEmployees() {
		numberOfEmployees++;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public int getNumberOfEmployees(){
		return numberOfEmployees;
	}
	
	public static void main(String[] args) {
		Employee employee = new Employee("John", "Doe");
		System.out.print(employee.getFirstName() + " ");
		System.out.println(employee.getLastName());
		System.out.println(employee.getNumberOfEmployees());
		Employee employee2 = new Employee("Charlie", "Misner");
		System.out.print(employee2.getFirstName() + " ");
		System.out.println(employee2.getLastName());
		System.out.println(employee2.getNumberOfEmployees());
		Employee employee3 = new Employee("Butch", "Cougar");
		System.out.print(employee3.getFirstName() + " ");
		System.out.println(employee3.getLastName());
		System.out.println(employee3.getNumberOfEmployees());
	}

}
