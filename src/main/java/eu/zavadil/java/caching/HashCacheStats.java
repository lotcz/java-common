package eu.zavadil.java.caching;

public class HashCacheStats {

	private final int cachedItems;

	public int getCachedItems() {
		return this.cachedItems;
	}

	private final int capacity;

	public int getCapacity() {
		return this.capacity;
	}

	public HashCacheStats(int cachedItems, int capacity) {
		this.cachedItems = cachedItems;
		this.capacity = capacity;
	}

}
