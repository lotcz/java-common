package eu.zavadil.java.queues;

import lombok.Getter;

@Getter
public abstract class SmartQueueProcessorBase<T> implements SmartQueueProcessor<T> {

	private final SmartQueue<T> queue;

	public SmartQueueProcessorBase(SmartQueue<T> queue) {
		this.queue = queue;
	}

	private SmartQueueProcessorState state = SmartQueueProcessorState.Idle;

	public abstract void processItem(T e);

	public void onBeforeProcessing() {

	}

	public void onAfterProcessing() {

	}

	@Override
	public void process() {
		this.state = SmartQueueProcessorState.Processing;
		try {
			this.onBeforeProcessing();
			while (this.queue.hasNext()) {
				T n = this.queue.next();
				if (n == null) {
					break;
				}
				this.processItem(n);
			}
			this.onAfterProcessing();
		} finally {
			this.queue.reset();
			this.state = SmartQueueProcessorState.Idle;
		}
	}

	public SmartQueueProcessorStats getStats() {
		SmartQueueProcessorState state = this.queue.isLoading() ? SmartQueueProcessorState.Loading : this.state;
		if (!state.equals(SmartQueueProcessorState.Idle)) {
			return new SmartQueueProcessorStats(
				this.queue.remaining(),
				this.queue.getLoaded(),
				this.queue.processed(),
				state
			);
		}
		return new SmartQueueProcessorStats(0, 0, 0, state);
	}
}
