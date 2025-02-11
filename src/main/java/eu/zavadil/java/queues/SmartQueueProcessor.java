package eu.zavadil.java.queues;

public interface SmartQueueProcessor<T> {

	SmartQueue<T> getQueue();

	SmartQueueProcessorState getState();

	default SmartQueueProcessorStats getStats() {
		return new SmartQueueProcessorStats(
			this.getQueue().getRemaining(),
			this.getQueue().getLoaded(),
			this.getState()
		);
	}

	void process();
}
