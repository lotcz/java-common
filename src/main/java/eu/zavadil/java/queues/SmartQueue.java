package eu.zavadil.java.queues;

public interface SmartQueue<T> {

	T next();

	default boolean hasNext() {
		return this.getRemaining() > 0;
	}

	long getRemaining();

	int getLoaded();

	int getProcessed();

	boolean isLoading();

	void reset();
}
