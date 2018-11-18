package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import hr.fer.zemris.parser.ComplexParser;

/**
 * Program for constructing fractal images using Newton-Raphson iteration, from
 * complex roots given by the user through the console.
 * 
 * @author MarinoK
 */
public class Newton {

	/** Polynomial given by the user, in form of a rooted polynomial. */
	private static ComplexRootedPolynomial polynomial;

	/** Derived polynomial, in regular form of a polynomial. */
	private static ComplexPolynomial derived;

	/** String for declaring the end of the input. */
	private static final Object EXIT_STRING = "done";

	/**
	 * Main method runs when the program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		List<Complex> roots = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		String input = null;
		int i = 0;
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

		while (true) {
			System.out.printf("Root %d>", ++i);
			input = scanner.next();
			if (input.trim().toLowerCase().equals(EXIT_STRING)) break;

			Complex root = ComplexParser.parse(input);
			roots.add(root);

		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		for (Complex root : roots) {
			System.out.println(root);
		}
		Complex[] rootsArray = roots.toArray(new Complex[roots.size()]);
		polynomial = new ComplexRootedPolynomial(rootsArray);
		derived = polynomial.toComplexPolynom().derive();

		System.out.println(polynomial);
		System.out.println(derived);
		scanner.close();
		FractalViewer.show(new NewtonFractalProducer());
	}

	/**
	 * Implementation of Callable interface used for calculating the Newton
	 * convergence.
	 */
	public static class CalculateNewton implements Callable<Void> {

		/** Limit of convergence. */
		private static final double moduleLimit = 1E-3;

		/**
		 * Two vectors are 'close' if their difference is shorter than this threshold.
		 */
		private static final double rootClosenessLimit = 1E-3;

		/** Minimal real value. */
		private double reMin;

		/** Maximal real value. */
		private double reMax;

		/** Minimal imaginary value. */
		private double imMin;

		/** Maximal imaginary value. */
		private double imMax;

		/** Total width. */
		private int width;

		/** Total height. */
		private int height;

		/** Minimal value of y axis. */
		private int yMin;

		/** Maximal value of y axis. */
		private int yMax;

		/** Maximal number of iteration before giving up on the convergence. */
		private int maxIter;

		/** Array used for coloring the screen. */
		private short[] data;

		/** Minimal value of x. */
		private int xMin;

		/** Maximal value of x. */
		private int xMax;

		/**
		 * Constructor for the CalculateNewton method.
		 * 
		 * @param reMin
		 *            Minimal real value
		 * @param reMax
		 *            Maximal real value
		 * @param imMin
		 *            Minimal imaginary value
		 * @param imMax
		 *            Maximal imaginary value
		 * @param width
		 *            Total width
		 * @param height
		 *            Total height
		 * @param yMin
		 *            Minimal value of y axis
		 * @param yMax
		 *            Maximal value of y axis
		 * @param maxIter
		 *            Maximal number of iteration before giving up on the convergence
		 * @param data
		 *            Array used for coloring the screen
		 */
		public CalculateNewton(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int maxIter, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = maxIter;
			this.data = data;
			this.xMin = 0;
			this.xMax = width;
		}

		@Override
		public Void call() {
			int offset = yMin * width;

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y, xMin, xMax, yMin, yMax, reMin, reMax, imMin, imMax);
					Complex zn1 = null;
					double module;
					int iter = 0;
					do {
						iter++;
						if (iter >= maxIter) break;
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
					} while (module > moduleLimit);
					short index = (short) polynomial.indexOfClosestRootFor(zn1, rootClosenessLimit);
					data[offset++] = (index == -1) ? 0 : index;
				}
			}

			return null;
		}

		/**
		 * Auxiliary method used for mapping the image to complex plain.
		 * 
		 * @param x
		 *            value of x
		 * @param y
		 *            value of y
		 * @param xMin2
		 *            minimal value of x
		 * @param xMax2
		 *            maximal value of x
		 * @param yMin2
		 *            minimal value of y
		 * @param yMax2
		 *            maximal value of y
		 * @param reMin2
		 *            minimal real value
		 * @param reMax2
		 *            maximal imaginary value
		 * @param imMin2
		 *            minimal value of imaginary
		 * @param imMax2
		 *            maximal value of imaginary
		 * @return complex number in relation to the given image
		 */
		private Complex mapToComplexPlain(int x, int y, int xMin2, int xMax2, int yMin2, int yMax2, double reMin2,
				double reMax2, double imMin2, double imMax2) {
			double real = x / (width - 1.0) * (reMax - reMin) + reMin;
			double im = (height - 1 - y) / (height - 1.0) * (imMax - imMin) + imMin;
			return new Complex(real, im);
		}
	}

	/**
	 * Implementation of IFractalProducer used for assigning the jobs to the
	 * threads, and paralyzing the computing.
	 * 
	 * @author MarinoK
	 */
	public static class NewtonFractalProducer implements IFractalProducer {

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");
			short[] data = new short[width * height];
			int maxIter = 16 * 16 * 16;

			int numberOfWorkers = Runtime.getRuntime().availableProcessors();
			ExecutorService threadPool = Executors.newFixedThreadPool(numberOfWorkers);
			int numberOfJobs = numberOfWorkers * 8;
			int oneJobHeight = height / numberOfJobs;

			List<Future<Void>> rezultati = new ArrayList<>();
			for (int i = 0; i < numberOfJobs; i++) {
				int yMin = i * oneJobHeight;
				int yMax = (i + 1) * oneJobHeight - 1;
				if (i == numberOfJobs - 1) {
					yMax = height - 1;
				}
				CalculateNewton posao = new CalculateNewton(reMin, reMax, imMin, imMax, width, height, yMin, yMax,
						maxIter, data);
				rezultati.add(threadPool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			threadPool.shutdown();
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}
}
