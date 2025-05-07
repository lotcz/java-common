package eu.zavadil.java.queues;

import eu.zavadil.java.iterators.SmartIterator;

public interface SmartQueue<T> extends SmartIterator<T> {

	int getLoaded();

	boolean isLoading();

	void reset();

}
