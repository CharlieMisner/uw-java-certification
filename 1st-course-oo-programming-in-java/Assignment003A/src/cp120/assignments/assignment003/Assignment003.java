package cp120.assignments.assignment003;

public class Assignment003 {
    public static void main(String[] args) {
        String dataLocation = "data/employee.csv";
        Report report = new Report(dataLocation); // Create instance of report class.
        report.parseData(); // Parse data from file.
        report.printReportToConsole(); // Prints the report to the console.
    }
}
