package eu.zavadil.java;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * Helper for caching values and expiring after defined amount of time.
 * Similar to Lazy<>, this will call provided supplier when value is missing, but also when it is expired.
 */
public class Cached<T> extends Lazy<T> {

	private final Duration lifetime;

	private Instant cacheExpires = null;

	public Cached(Duration cacheLifetime, Supplier<T> supplier) {
		super(supplier);
		this.lifetime = cacheLifetime;
	}

	@Override
	public synchronized T get() {
		if (this.cacheExpires == null || this.cacheExpires.isBefore(Instant.now())) {
			this.cache = null;
		}
		if (this.cache == null) {
			this.cache = this.supplier.get();
			this.cacheExpires = Instant.now().plus(this.lifetime);
		}
		return this.cache;
	}

}
