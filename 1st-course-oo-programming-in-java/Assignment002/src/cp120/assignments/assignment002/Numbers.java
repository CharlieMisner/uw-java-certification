package cp120.assignments.assignment002;
import java.math.BigInteger;

public class Numbers {
	
// Least Common Multiple methods

	/**
	 * Returns the least common multiple of two positive integers.
	 * 
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	public static int lcm(int firstNumber, int secondNumber) {
		int greatestCommonDenominator = gcd(firstNumber, secondNumber);
		int leastCommonMultiple = (greatestCommonDenominator > 0) ? (firstNumber * secondNumber) / greatestCommonDenominator : 0;

		return leastCommonMultiple;
	}

	/**
	 * Uses Euclid's algorithm to return the greatest common denominator of two
	 * positive integers.
	 * 
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	public static int gcd(int firstNumber, int secondNumber) {
		// Sort numbers such that the greater number is first.
		int previousModulus = (firstNumber > secondNumber) ? firstNumber : secondNumber;
		int currentModulus = (firstNumber > secondNumber) ? secondNumber : firstNumber;;

		// Euclid's algorithm
		while (currentModulus != 0) {
			int loopModulo;
			loopModulo = previousModulus % currentModulus;
			previousModulus = currentModulus;
			currentModulus = loopModulo;
		}

		return previousModulus;
	}

// Factorial Methods
	
	/**
	 * Accepts a number as a BigInteger and returns the factorial of the number as a BigInteger.
	 * @param number
	 * @return
	 */
	public static BigInteger factorial(BigInteger number) {
		return sharedFactorialOperations(number);
	}
	
	/**
	 * Accepts a number as an integer and returns the factorial of the number as a BigInteger.
	 * @param number
	 * @return
	 */
	public static BigInteger factorial(int number) {
		BigInteger bigNumber = BigInteger.valueOf(number);
		return sharedFactorialOperations(bigNumber);
	}
	
	/**
	 * Performs the factorial operations that are common to factorial(int) and factorial(BigInteger).
	 * @param number
	 * @return
	 */
	public static BigInteger sharedFactorialOperations(BigInteger number) {
		BigInteger factorialOfNumber = BigInteger.valueOf(1);
		if (number.intValue() > 0){
			for (long i = 1; i <= number.intValue(); i++){
				BigInteger bigIndex = BigInteger.valueOf(i);
				factorialOfNumber = factorialOfNumber.multiply(bigIndex);
			}
		}
		return factorialOfNumber;
	}
	
// Stars Method
	
	public static void stars(){
		for (int i = 0; i < 7; i++){
			if((i % 2) == 0){
				
				for (int j = 0; j < 7; j++){
					System.out.print("# ");
				}
				System.out.println();
				
			} else {
				
				for (int j = 0; j < 6; j++){
					System.out.print(" #");
				}
				System.out.println();
				
			}
		}
	}
	
	public static void main( String[] args ){
		stars();
	}

}
