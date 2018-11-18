package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program used to calculate the factorial of a given number, if the number is
 * in desired range. In other cases, the program will print out the appropriate
 * message. Program will loop until the string 'kraj' is input.
 * 
 * @author MarinoK
 * @version 1.0
 *
 */
public class Factorial {

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            command line arguments, usage not expected
	 */
	public static void main(String[] args) {
		loadInput();
	}

	/**
	 * Method used to scan the user input from the console/command line ***********.
	 */
	private static void loadInput() {
		String token;
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.print("Unesite broj > ");
			token = scan.next();
			if (token.toLowerCase().equals("kraj")) {
				break;
			}
			filterInput(token);
		}
		scan.close();
		System.out.println("Doviđenja.");
	}

	/**
	 * Method used to filter out the undesirable inputs and to parse those which
	 * satisfy given conditions.
	 * 
	 * @param userInput
	 *            string that needs to be filtered and parsed
	 * 
	 */
	private static void filterInput(String userInput) {
		int parsedInput;
		
		try {
			parsedInput = Integer.parseInt(userInput);
		} catch (NumberFormatException parseFailed) {
			System.out.println("'" + userInput + "' nije cijeli broj.");
			return;
		}
		
		if (parsedInput < 1 || parsedInput > 20) {
			System.out.println("'" + parsedInput + "' nije broj u dozvoljenom rasponu.");
		} else {
			System.out.println(parsedInput + "! = " + calculateFactorial(parsedInput));
		}
	}

	/**
	 * Method used to calculate the factorial of a given integer using recursion.
	 * Visibility set to package, so the method could be tested.
	 * 
	 * @param parsedInput
	 *            positive whole number whose factorial needs to be calculated
	 * @return input number's factorial
	 * @throws RuntimeException
	 *             in case a negative number is input
	 */
	static long calculateFactorial(long parsedInput) throws RuntimeException {
		if (parsedInput < 0) {
			throw new RuntimeException("Factorial is defined only for positive integers.");
		}
		long product = 1;
		if (parsedInput == 0) {
			return product;
		} else {
			return calculateFactorial(parsedInput - 1) * parsedInput;
		}
	}

}
