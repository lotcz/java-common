package eu.zavadil.java.iterators;

public interface SmartIterator<T> extends BasicIterator<T> {

	long remaining();

	default boolean hasNext() {
		return this.remaining() > 0;
	}

	long processed();

}
