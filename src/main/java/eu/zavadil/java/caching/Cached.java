package eu.zavadil.java.caching;

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

	public boolean isExpired() {
		return this.cacheExpires == null || this.cacheExpires.isBefore(Instant.now());
	}

	@Override
	public void setCache(T value) {
		super.setCache(value);
		this.cacheExpires = (value == null) ? null : Instant.now().plus(this.lifetime);
	}

	@Override
	public synchronized T get() {
		if (this.isExpired()) {
			this.setCache(null);
		}
		if (this.getCache() == null) {
			this.setCache(this.supplier.get());
		}
		return this.getCache();
	}

}
