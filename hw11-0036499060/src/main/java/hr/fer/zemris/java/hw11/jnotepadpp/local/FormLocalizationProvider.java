package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class derived from LocalizationProviderBridge. Connects the bridge to the
 * original provider when the window opens, and disconnects it when it closes.
 * 
 * @author MarinoK
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor for the FormLocalizationProvider.
	 * 
	 * @param provider
	 *            given localization provider
	 * @param frame
	 *            to be tracked for changes (opening, closing)
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				connected();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnected();
			}
		});

	}

}
