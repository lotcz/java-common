package eu.zavadil.java.caching;

import java.time.Instant;
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
		if (this.getCache() == null) {
			this.setCache(this.supplier.get());
		}
		return this.getCache();
	}

	public T getCache() {
		return this.cache;
	}

	public void setCache(T value) {
		this.cache = value;
	}

}
