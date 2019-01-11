package cp120.assignments.assignment004;

public class Assignment004 {

    public static void main(String[] args) {
        String dataLocation = "data/employee.csv";
        Report report = new Report(dataLocation); // Create instance of report class.
        report.parseData(); // Parse data from file.
        report.printReportToConsole(); // Prints the report to the console.
    }
}
