package eu.zavadil.java.queues;

public class SmartQueueProcessorStats {

	private final long remaining;

	public long getRemaining() {
		return this.remaining;
	}

	private final int loaded;

	public int getLoaded() {
		return this.loaded;
	}

	private final int processed;

	public int getProcessed() {
		return this.processed;
	}

 	private final SmartQueueProcessorState state;

	public SmartQueueProcessorState getState() {
		return this.state;
	}

	public SmartQueueProcessorStats(long remaining, int loaded, int processed, SmartQueueProcessorState state) {
		this.remaining = remaining;
		this.loaded = loaded;
		this.processed = processed;
		this.state = state;
	}
}
