package cp120.assignments.assignment001;

import java.util.Scanner;

public class Assignment001 {

	public static void main(String[] args) {
		
	// Scanner Exercise:
		// Initialize string and scanner;
		String initialString = "127 327 2147483645 -9234 3.4567 294555.789 true c";
		Scanner scanner = new Scanner(initialString);
		
		// Use scanner to parse values from string with correct type.
		byte byteValue = scanner.nextByte();
		short shortValue = scanner.nextShort();
		int intValue = scanner.nextInt();
		long longValue = scanner.nextLong();
		float floatValue = scanner.nextFloat();
		double doubleValue = scanner.nextDouble();
		boolean booleanValue = scanner.nextBoolean();
		char charValue = scanner.next().charAt(0);
		
		// Print values parsed from scanner.
		System.out.println(byteValue);
		System.out.println('\n');
		System.out.println(shortValue);
		System.out.println('\n');
		System.out.println(intValue);
		System.out.println('\n');
		System.out.println(longValue);
		System.out.println('\n');
		System.out.println(floatValue);
		System.out.println('\n');
		System.out.println(doubleValue);
		System.out.println('\n');
		System.out.println(booleanValue);
		System.out.println('\n');
		System.out.println(charValue);
		System.out.println('\n');
		
		scanner.close();
		
	// Operator Exercise:
		// Evaluate expression as integer (per instructor's request).
		int expression = 127 + (12 * 45) + (1 % 37) / 4;
		
		// Print evaluated expression.
		System.out.println(expression);
	}

}
