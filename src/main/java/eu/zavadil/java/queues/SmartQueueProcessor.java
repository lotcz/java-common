package eu.zavadil.java.queues;

public interface SmartQueueProcessor<T> {

	SmartQueue<T> getQueue();

	SmartQueueProcessorState getState();

	default SmartQueueProcessorStats getStats() {
		return new SmartQueueProcessorStats(
			this.getQueue().remaining(),
			this.getQueue().getLoaded(),
			this.getQueue().processed(),
			this.getState()
		);
	}

	void process();
}
