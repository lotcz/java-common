package eu.zavadil.java;

public class JavaHeapStats {

	private final long heapSize;

	public long getHeapSize() {
		return this.heapSize;
	}

	private final long heapMaxSize;

	public long getHeapMaxSize() {
		return this.heapMaxSize;
	}

	private final long heapFreeSize;

	public long getHeapFreeSize() {
		return this.heapFreeSize;
	}

	public JavaHeapStats(
		long heapSize,
		long heapMaxSize,
		long heapFreeSize
	) {
		this.heapSize = heapSize;
		this.heapMaxSize = heapMaxSize;
		this.heapFreeSize = heapFreeSize;
	}

	public static JavaHeapStats ofCurrent() {
		return new JavaHeapStats(
			Runtime.getRuntime().totalMemory(),
			Runtime.getRuntime().maxMemory(),
			Runtime.getRuntime().freeMemory()
		);
	}

}
