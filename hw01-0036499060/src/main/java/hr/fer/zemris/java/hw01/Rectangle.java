package hr.fer.zemris.java.hw01;

import java.util.Locale;
import java.util.Scanner;

/**
 * Program calculates surface and perimeter of the rectangle determined by
 * parameters given through command line or as program arguments.
 * 
 * @author MarinoK
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Constant, defines number of program arguments in a desirable outcome.
	 */
	public static final int NUMBER_OF_ARGUMENTS = 2;

	/**
	 * Constant, defines a possible return value in checkInput method.
	 */
	private static final double ERROR = -1;

	/**
	 * Scanner which reads from the system input.
	 */
	static Scanner scan = new Scanner(System.in);

	/**
	 * The main method that runs when the program is run.
	 * 
	 * @param args
	 *            width and height of the rectangle
	 */
	public static void main(String[] args) {
		double width = 0;
		double height = 0;
		boolean valid = false;

		scan.useLocale(Locale.getDefault()); // using current OS's locale to avoid errors with double numbers

		if (args.length == NUMBER_OF_ARGUMENTS && checkInput(args[0]) != ERROR && checkInput(args[1]) != ERROR) {
			width = checkInput(args[0]);
			height = checkInput(args[1]);
			if(width>0 && height>0) {
				valid = true;
			}
		} else if (args.length == 0) {
			width = demandInput("širinu");
			height = demandInput("visinu");
			valid = true;
		}

		if (valid) {
			calculateRectangle(width, height);
		} else {
			System.out.println(
					"Nevaljan broj validnih argumenata. Dozvoljena su dva ili niti jedan argument prilikom pokretanja.");
		}
		scan.close();
	}

	/**
	 * Method used to force the user to give a legal input.
	 * 
	 * @param requestedValue
	 *            instruction for the user, to be printed
	 * @return desired user input in a form of a double
	 */
	private static double demandInput(String requestedValue) {
		String token = "";

		while (true) {
			System.out.print("Unesite " + requestedValue + " > ");
			token = scan.next();
			if (checkInput(token) != ERROR) {
				break;
			}
		}
		return checkInput(token);
	}

	/**
	 * Method used to check is the user input satisfactory.
	 * 
	 * @param input
	 *            users input in a form of a string
	 * @return in case the input is desired, returns parsed value of input.
	 *         Otherwise, returns ERROR
	 */
	private static double checkInput(String input) {
		double parsedValue = 0;
		try {
			parsedValue = Double.parseDouble(input);
		} catch (NumberFormatException parseFailed) {
			System.out.println("'" + input + "' se ne može protumačiti kao broj.");
			return ERROR;
		}

		if (parsedValue > 0) {
			return parsedValue;
		} else if (parsedValue < 0) {
			System.out.println("Unijeli ste negativnu vrijednost.");
		} else {
			System.out.println("Ne postoji pravokutnik sa stranicom duljine nula.");
		}
		return ERROR;
	}

	/**
	 * Method used to calculate and output the surface and the perimeter of the
	 * rectangle.
	 * 
	 * @param width
	 *            of the rectangle, must be a positive number
	 * @param height
	 *            of the rectangle, must be a positive number
	 */
	private static void calculateRectangle(double width, double height) {
		System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height,
				width * height, 2 * width + 2 * height);
	}
}
