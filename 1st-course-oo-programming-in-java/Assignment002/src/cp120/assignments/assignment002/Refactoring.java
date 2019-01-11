package cp120.assignments.assignment002;


public class Refactoring {
	public static void main(String[] args) {
		// (1) Box 1: Find the area and check if prime
		int xDimension1 = 10;
		int yDimension1 = 20;
		String nameBox1 = "Box 1";
		int Area1 = area(xDimension1, yDimension1, nameBox1);

		// (2) Box 2: Find the area and check if prime
		int xDimension2 = 31;
		int yDimension2 = 1;
		String nameBox2 = "Box 2";
		int Area2 = area(xDimension2, yDimension2, nameBox2);
		
		// (3) Box 3: Find the area and check if prime
		int xDimension3 = 14;
		int yDimension3 = 12;
		String nameBox3 = "Box 3";
		int Area3 = area(xDimension3, yDimension3, nameBox3);
	}
	
	/**
	 * Calculates the area of a rectangle given the dimensions, and checks if the area is prime.
	 * @param xDimension
	 * @param yDimension
	 * @param name
	 * @return area
	 */
	public static int area (int xDimension, int yDimension, String name) {
		// Calculate area from dimensions.
		int area = xDimension * yDimension; 
		System.out.printf("%s area = %d" + "\n", name, area);
		
		// Check if area is prime and print message.
		if (isPrime(area)) {
			System.out.printf("Area "+ area + " is prime! \n");
		}
		
		return area;
	}

	/**
	 * Returns true if a given integer is prime.
	 * <p>
	 * <strong>Precondition: </strong> <em>num</em> is a non-negative integer.
	 * </p>
	 * <p>
	 * <strong>Postcondition: </strong> If <em>num</em> is 0 or 1 the result
	 * will be false.
	 * </p>
	 * @param num The given integer
	 * @return True if the given integer is prime.
	 */
	public static boolean isPrime(int num) {
		boolean rcode = true;
		if (num < 2) {
			return false;
		} else if (num == 2)
			return true;
		else if (num % 2 == 0)
			return false;
		else {
			int last = num / 2;
			for (int inx = 3; inx < last && rcode; ++inx)
				if (num % inx == 0)
					return false;
		}
		return rcode;
	}   
}
