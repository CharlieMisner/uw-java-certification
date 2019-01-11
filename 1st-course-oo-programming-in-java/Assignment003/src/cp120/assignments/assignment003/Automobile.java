package cp120.assignments.assignment003;

public class Automobile {
	
	final int MPG = 33;
	final double FUEL_TANK_SIZE = 13.2;
	final double GAS_PRICE = 4.00;
	
	private double fuelLevel;
	private double odometerReading;
	
	public Automobile(double fLevel, double oReading) {
		this.fuelLevel = fLevel;
		this.odometerReading = oReading;
	}
	
	/**
	 * If enough fuel is available, the car will drive the required distance and adjust the fuel level.
	 * @param distance
	 */
	public void drive(double distance) {
		double fuelRequired = distance / this.MPG;
		
		if(this.fuelLevel < fuelRequired){
			System.out.printf("There is not enough fuel to drive %.2f miles. The current fuel level is %.2f gallons.", distance, this.fuelLevel);
		} else {
			this.fuelLevel -= distance / this.MPG;
			this.odometerReading += distance;
			System.out.printf("You drove %.2f miles. The current fuel level is %.2f gallons."
					+ "the odometer reads %.1f miles", distance, this.fuelLevel, this.odometerReading);
		}
		System.out.println(""); // Line break.
	}
	
	
	/**
	 * Adds fuel to vehicle. Adjusts quantity and determines change as needed.
	 * @param dollarAmount
	 */
	public void refuel(double dollarAmount){
		double fuelQuantityRequested = dollarAmount / this.GAS_PRICE;
		double remainingSpaceInTank = this.FUEL_TANK_SIZE - this.fuelLevel;
		
		if(remainingSpaceInTank == 0){
			System.out.printf("The fuel tank is already full.", dollarAmount, fuelQuantityRequested);
		} else if(fuelQuantityRequested <= remainingSpaceInTank) {
			this.fuelLevel += fuelQuantityRequested;
			System.out.printf("Please pay %.2f dollars for %.2f gallons of fuel", dollarAmount, fuelQuantityRequested);
		} else if (fuelQuantityRequested > remainingSpaceInTank) {
			this.fuelLevel = this.FUEL_TANK_SIZE;
			double payment = remainingSpaceInTank * this.GAS_PRICE;
			double change = dollarAmount - payment;
			System.out.printf("Please pay %.2f dollars for %.2f gallons of fuel. Your change is %.2f dollars.", payment, remainingSpaceInTank, change);
		}
		System.out.println(""); // Line break.
	}
	
	public double getFuelLevel(){
		return this.fuelLevel;
	}
	
	public double getMileage(){
		return this.odometerReading;
	}
	
	public double getRange(){
		return this.fuelLevel * this.MPG;
	}
	
	public double getFuelTankSize(){
		return this.FUEL_TANK_SIZE;
	}

	public static void main(String[] args) {
		Automobile car1 = new Automobile(6.6, 10000);
		car1.refuel(10.00);
		car1.drive(350.00);
		car1.refuel(30.00);
		car1.drive(350.00);
		Automobile car2 = new Automobile(13.2, 20000);
		car2.refuel(10.00);
		car2.drive(350.00);
		car2.refuel(30.00);
		car2.drive(350.00);
	}
	
}
