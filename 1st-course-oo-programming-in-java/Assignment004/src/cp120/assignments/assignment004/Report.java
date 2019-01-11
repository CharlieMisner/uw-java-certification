package cp120.assignments.assignment004;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Report {

    String filePath;

    public Report(String path){
        this.filePath = path;
    }

    /**
     * Parses data from file at specified location and generates an array of Employees.
     */
    public void parseData(){
        this.getDataFromFile();
        this.createEmployeeObjectsArray();
    }

    BufferedReader reader;
    /**
     * Creates an instance of buffered reader using the provided csv file.
     */
    private void getDataFromFile(){
        try {
            this.reader = new BufferedReader(new FileReader(this.filePath));
        } catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
    }


    List<Employee> employeeList = new ArrayList<>();;
    /**
     * Creates an array of employees from reader.
     */
    private void createEmployeeObjectsArray() {
        try{
            String line;
            while((line = reader.readLine()) != null) {
                String[] rawDataArr = line.split(",");
                Employee employee = this.createEmployee(rawDataArr);
                employeeList.add(employee);
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Takes in an array of raw employee data, instantiates an employee object, and returns that employee object.
     * @param array
     * @return employee
     */
    private Employee createEmployee(String[] array){
        Address address = new Address(array[3], array[4], array[5], array[6], array[7]);
        Company company = new Company(array[2], array[11], array[8], address);
        Employee employee = new Employee(array[0], array[1], array[9], array[10], company);
        return employee;
    }

    /**
     * Prints formatted report of employee properties to console.
     */
    public void printReportToConsole(){
        for(int i=0; i < employeeList.size(); i++) {
            Employee currentEmployee = employeeList.get(i);
            Company currentCompany = currentEmployee.getEmployer();
            Address currentAddress = currentCompany.getCompanyAddress();
            System.out.println("========================================");
            System.out.printf("%s %s",currentEmployee.getFirstName(), currentEmployee.getLastName());
            System.out.println();
            System.out.printf("      %s",currentCompany.getCompanyName());
            System.out.println();
            System.out.printf("      %s",currentAddress.getStreet());
            System.out.println();
            System.out.printf("      %s, %s %s",currentAddress.getCity(), currentAddress.getState(), currentAddress.getZip());
            System.out.println();
            System.out.printf("      %s",currentCompany.getCompanyWebsite());
            System.out.println();
        }
        System.out.println("========================================");
    }
}
