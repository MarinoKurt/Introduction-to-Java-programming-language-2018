package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class used to communicate with the original localization provider.
 * 
 * @author MarinoK
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** True if the bridge is connected to the singleton. */
	private boolean connected;

	/** Localization listener. */
	private ILocalizationListener listener;

	/** Localization provider. */
	private ILocalizationProvider parent;

	/**
	 * Constructor for LocalizationProviderBridge.
	 * 
	 * @param parent
	 *            original localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	/**
	 * Method used to disconnect from the original localization provider.
	 */
	public void disconnected() {
		if (!connected) return;
		connected = false;
		parent.removeLocalizationListener(listener);
	}

	/**
	 * Method used to connect to the original localization provider.
	 */
	public void connected() {
		if (connected) return;
		connected = true;
		parent.addLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}

}
