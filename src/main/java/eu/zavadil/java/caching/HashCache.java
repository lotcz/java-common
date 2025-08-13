package eu.zavadil.java.caching;

import java.time.Duration;
import java.util.HashMap;

/**
 * Caches multiple values by key. Values have expiration time set to 5 minutes by default.
 */
public abstract class HashCache<TKey, TVal> {

	private static final int DEFAULT_MAX_ITEMS = 100;

	private static final Duration DEFAULT_MAX_AGE = Duration.ofMinutes(5);

	protected final int maxItems;

	protected final Duration maxAge;

	protected final HashMap<TKey, Cached<TVal>> cache = new HashMap<>();

	public HashCache(int maxItems, Duration maxAge) {
		this.maxItems = maxItems;
		this.maxAge = maxAge;
	}

	public HashCache() {
		this(DEFAULT_MAX_ITEMS, DEFAULT_MAX_AGE);
	}

	protected abstract TVal load(TKey key);

	protected abstract TVal save(TVal val);

	protected abstract TKey extractKey(TVal val);

	public Cached<TVal> obtainCached(TKey key) {
		Cached<TVal> cache = this.cache.get(key);
		if (cache == null) {
			cache = new Cached<>(this.maxAge, () -> this.load(key));
			this.cache.put(key, cache);
		}
		return cache;
	}

	public TVal get(TKey key) {
		return this.obtainCached(key).get();
	}

	public TVal set(TKey key, TVal val) {
		if (key == null) {
			TVal saved = this.save(val);
			key = this.extractKey(saved);
			this.obtainCached(key).setCache(saved);
			return saved;
		} else {
			this.obtainCached(key).setCache(val);
			return this.save(val);
		}
	}

	public TVal set(TVal val) {
		return this.set(this.extractKey(val), val);
	}

	public void reset(TKey key) {
		this.cache.remove(key);
	}

	public void reset() {
		this.cache.clear();
	}

	public HashCacheStats getStats() {
		return new HashCacheStats(
			this.cache.size(),
			this.maxItems
		);
	}
}
