package eu.zavadil.java;

import java.util.function.Supplier;

/**
 * Helper for lazy initialization.
 */
public class Lazy<T> implements Supplier<T> {

	protected final Supplier<T> supplier;

	protected T cache = null;

	public Lazy(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	public T get() {
		if (this.cache == null) {
			this.cache = this.supplier.get();
		}
		return this.cache;
	}

}
