package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton which provides the translations of keywords in every available
 * language.
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Constant for the path of the translations. */
	private static final String SOURCE_PATH = "hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi";
	
	/** Current language. */
	private String language;

	/** Resource bundle. */
	private ResourceBundle bundle;

	/** Only instance of Localization Provider created for this application. */
	private static LocalizationProvider instance = new LocalizationProvider();;

	/**
	 * Private constructor for the LocalizationProvider singleton.
	 */
	private LocalizationProvider() {
		this.language = Locale.ENGLISH.getLanguage();
		this.bundle = ResourceBundle.getBundle(SOURCE_PATH, Locale.forLanguageTag(language));
		fire();
	}

	/**
	 * Getter for the singleton.
	 * 
	 * @return only instance of the LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Setter for the language.
	 * 
	 * @param language
	 *            to be set
	 */
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle(SOURCE_PATH, Locale.forLanguageTag(language));
		fire();

	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
