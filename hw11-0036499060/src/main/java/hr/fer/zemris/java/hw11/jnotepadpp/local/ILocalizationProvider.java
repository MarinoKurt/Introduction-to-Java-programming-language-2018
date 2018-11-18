package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Subject of the Observer design pattern. Offers the service of fetching
 * localized strings by the given key.
 *
 */
public interface ILocalizationProvider {

	/**
	 * @param l
	 *            listener to be added
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * @param l
	 *            listener to be removed
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * @param key
	 *            used to fetch the localized string under that key
	 * @return localized string
	 * 
	 */
	String getString(String key);

	/**
	 * @return current language of the provider
	 */
	String getCurrentLanguage();

}
