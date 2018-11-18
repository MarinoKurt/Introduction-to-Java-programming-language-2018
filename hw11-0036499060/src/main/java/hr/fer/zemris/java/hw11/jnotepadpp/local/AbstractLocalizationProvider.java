package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract implementation of Localization Provider, adds the functionality to
 * register, unregister and inform listeners.
 * 
 * @author MarinoK
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** List of observers. */
	private List<ILocalizationListener> observers;

	/**
	 * Default constructor for AbstractLocalizationProvider.
	 */
	public AbstractLocalizationProvider() {
		this.observers = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l);
		observers.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		Objects.requireNonNull(l);
		observers.remove(l);
	}

	/**
	 * Auxiliary method used to notify all the observers that localization has
	 * changed.
	 */
	public void fire() {
		List<ILocalizationListener> observersCopy = new ArrayList<>(observers);
		for (ILocalizationListener obs : observersCopy) {
			if (obs == null) continue;
			obs.localizationChanged();
		}
	}
}
