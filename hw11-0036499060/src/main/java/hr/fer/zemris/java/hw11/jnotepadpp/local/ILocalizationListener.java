package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Contract between the Listener and the Subject.
 */
public interface ILocalizationListener {

	/**
	 * Alerts the listener whenever the localization changes.
	 */
	void localizationChanged();
}
