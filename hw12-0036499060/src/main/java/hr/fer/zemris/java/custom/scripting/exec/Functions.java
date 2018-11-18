package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class containing the functions for the engine to work with.
 * 
 * @author MarinoK
 */
public class Functions {

	/** Temporary stack of objects. */
	private Stack<Object> tempStack;

	/** Context itself. */
	private RequestContext rc;

	/** Map of functions. */
	private Map<String, Runnable> functions;

	/**
	 * Constructor of the Functions.
	 * 
	 * @param tempStack
	 *            to work with
	 * @param rc
	 *            context itself
	 */
	public Functions(Stack<Object> tempStack, RequestContext rc) {
		this.tempStack = tempStack;
		this.rc = rc;
		functions = new HashMap<>();
		addFunctions();
	}

	/**
	 * Auxiliary method to add all the required functions to the map.
	 */
	private void addFunctions() {
		functions.put("sin", () -> {
			Object stackTop = tempStack.pop();
			double operand = 0;
			try {
				operand = Double.valueOf(String.valueOf(stackTop));
			} catch (NumberFormatException ex) {
				System.err.println("Problem parsing the sin argument");
			}
			Double result = Math.sin(Math.toRadians(operand));
			tempStack.push(result);
		});

		functions.put("decfmt", () -> {
			String format = String.valueOf(tempStack.pop());
			Double arg = null;
			try {
				arg = Double.valueOf(String.valueOf(tempStack.pop()));
			} catch (NumberFormatException nfe) {
				System.err.println("Problem parsing the decfmt argument");
			}
			DecimalFormat decFormat = new DecimalFormat(format);
			String result = decFormat.format(arg);
			tempStack.push(result);
		});

		functions.put("dup", () -> {
			Object x = tempStack.pop();
			tempStack.push(x);
			tempStack.push(x);
		});

		functions.put("swap", () -> {
			Object a = tempStack.pop();
			Object b = tempStack.pop();
			tempStack.push(b);
			tempStack.push(a);
		});

		functions.put("setMimeType", () -> {
			rc.setMimeType((String) tempStack.pop());
		});

		functions.put("paramGet", () -> {
			Object defaultValue = tempStack.pop();
			String name = (String) tempStack.pop();
			String value = rc.getParameter(name);
			tempStack.push(value == null ? defaultValue : value);
		});

		functions.put("pparamGet", () -> {
			Object defaultValue = tempStack.pop();
			String name = (String) tempStack.pop();
			String value = rc.getPersistentParameter(name);
			tempStack.push(value == null ? defaultValue : value);
		});

		functions.put("pparamSet", () -> {
			String name = String.valueOf(tempStack.pop());
			String value = String.valueOf(tempStack.pop());
			rc.setPersistentParameter(name, value);
		});

		functions.put("pparamDel", () -> {
			String name = String.valueOf(tempStack.pop());
			rc.removePersistentParameter(name);
		});

		functions.put("tparamGet", () -> {
			Object defaultValue = tempStack.pop();
			String name = String.valueOf(tempStack.pop());
			String value = rc.getTemporaryParameter(name);
			tempStack.push(value == null ? defaultValue : value);
		});

		functions.put("tparamSet", () -> {
			String name = String.valueOf(tempStack.pop());
			String value = String.valueOf(tempStack.pop());
			rc.setTemporaryParameter(name, value);
		});

		functions.put("tparamDel", () -> {
			String name = String.valueOf(tempStack.pop());
			rc.removeTemporaryParameter(name);
		});
	}

	/**
	 * Getter for the specific function.
	 * 
	 * @param name
	 *            of the function
	 * @return function as a Runnable object
	 */
	public Runnable getFunction(String name) {
		return functions.get(name);
	}

}
