package tst;

import cp120.assignments.assignment003.Automobile;

public class AutoDriver
{
	/** 
	 * Main driver to create, drive and refuel an automobile.
	 * @author Kat
	 */
    public static void main( String[] args )
    {
        testDrive();
    }
    
    public static void testDrive() {
       
   	 System.out.println("Taking car1 for a drive...");
    	 Automobile car1 = new Automobile(6.6, 10_000.00);
       car1.refuel(10.00);			// Put 10.00 worth of fuel
       car1.drive(350.00);			// Drive 350 miles (not enough fuel)
       car1.refuel(30.00);			// Put 30.00 worth of fuel (too much fuel)
       car1.drive(350.00);
       System.out.println();
       
       System.out.println("Taking car2 for a drive....");
       Automobile car2 = new Automobile(13.2, 20_000.00);
       car2.refuel(10.00);			// Full tank already
       car2.drive(350.00);			// Drive 350 miles  
       car2.refuel(30.00);			// Put 30.00 worth of fuel 
       car2.drive(350.00);			// Not enough
    }
}