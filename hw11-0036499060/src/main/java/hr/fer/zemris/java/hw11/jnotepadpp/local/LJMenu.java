package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Extension of JMenu which supports localization.
 * 
 * @author MarinoK
 */
public class LJMenu extends JMenu {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @param key
	 *            of translations in properties files
	 * @param prov
	 *            localization provider
	 * 
	 */
	public LJMenu(String key, ILocalizationProvider prov) {
		ILocalizationListener listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(prov.getString(key));
			}
		};
		prov.addLocalizationListener(listener);
		setText(prov.getString(key));
	}

}
