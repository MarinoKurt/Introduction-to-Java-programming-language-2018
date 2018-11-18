package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of the ListModel that works with integers.
 * 
 * @author MarinoK
 */
public class PrimListModel implements ListModel<Integer> {

	/** List of elements this model contains. */
	private List<Integer> elements;

	/** List of listeners subscribed to changes in this model. */
	private List<ListDataListener> observers;

	/**
	 * Constructor for the PrimListModel.
	 */
	public PrimListModel() {
		this.elements = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.elements.add(1);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	/**
	 * Method used for adding the next prime to the list, and notifying all the
	 * subscribed observers that a change happened.
	 */
	public void next() {
		int pos = elements.size();

		elements.add(calculateNextPrime());

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : observers) {
			l.intervalAdded(event);
		}
	}

	/**
	 * Auxiliary method used for calculating the next prime bigger than the last
	 * given prime.
	 * 
	 * @return next prime, or -1 if the overflow happened
	 */
	private int calculateNextPrime() {

		for (int i = elements.get(elements.size() - 1) + 1; i < Integer.MAX_VALUE; ++i) {
			boolean isPrime = true;
			for (int check = 2; check < i; ++check) {
				if (i % check == 0) isPrime = false;
			}
			if (isPrime) {
				return i;
			}
		}
		return -1;
	}

}
