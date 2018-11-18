package hr.fer.zemris.java.custom.scripting.demo;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demonstration for the SmartScriptEngine copied from homework file.
 */
public class EngineDemo3 {

	/**
	 * Main method runs when program is run.
	 * 
	 * @param args
	 *            usage not expected
	 */
	public static void main(String[] args) {
		
		String documentBody = TreeWriter.loader("./webroot/scripts/brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
}
