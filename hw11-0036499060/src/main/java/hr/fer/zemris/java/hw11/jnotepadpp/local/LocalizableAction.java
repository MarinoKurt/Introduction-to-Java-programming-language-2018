package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Extension of AbstractAction which works with localized action names,
 * descriptions and mnemonics.
 * 
 * @author MarinoK
 */
public abstract class LocalizableAction extends AbstractAction {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** Key of the translations in properties files. */
	private String key;

	/** Localization provider. */
	private ILocalizationProvider prov;

	/** Localization listener. */
	private ILocalizationListener listener;

	/**
	 * Constructor for LocalizableAction.
	 * 
	 * @param key
	 *            of translations
	 * @param prov
	 *            localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider prov) {
		this.key = key;
		this.prov = prov;
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fetchFromBundle();
			}
		};
		prov.addLocalizationListener(listener);
		fetchFromBundle();
	}

	/**
	 * Auxiliary method to fetch the translations from the resource bundle, and put
	 * them in the action map.
	 */
	private void fetchFromBundle() {
		String[] values = prov.getString(key).split("/");
		putValue(Action.NAME, values[0]);
		putValue(Action.SHORT_DESCRIPTION, values[1]);
		putValue(Action.MNEMONIC_KEY, Integer.valueOf(values[2].charAt(0)));
	}

}
