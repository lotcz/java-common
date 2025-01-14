package eu.zavadil.java;

import eu.zavadil.java.util.ExceptionUtils;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

/**
 * This simple object will wrap an executable action and then try to execute it.
 * When the action fail with an exception, it attempts to run it again until it succeeds or until we run out of defined number of attempts.
 * Optionally, delay between attempts can be specified.
 * If even that last attempt fails, then an exception will be thrown.
 */
public class ActionRepeater<ActionResultType> {

	static final int DEFAULT_MAX_ATTEMPTS = 3;

	static final BiConsumer<Exception, Integer> DEFAULT_EXCEPTION_HANDLER =
		(Exception e, Integer attemptsLeft) ->
			System.out.println(
				String.format(
					"ActionRepeater attempt failed, %d attempts remaining: %s",
					attemptsLeft,
					ExceptionUtils.getMessage(e)
				)
			);

	private final int maxAttempts;

	private final Duration delay;

	private final Callable<ActionResultType> action;

	private final BiConsumer<Exception, Integer> exceptionHandler;

	public ActionRepeater(
		Callable<ActionResultType> action,
		BiConsumer<Exception, Integer> exceptionHandler,
		int maxAttempts,
		Duration delay
	) {
		this.action = action;
		this.exceptionHandler = exceptionHandler;
		this.maxAttempts = maxAttempts;
		this.delay = delay;
	}

	public ActionRepeater(Callable<ActionResultType> action, BiConsumer<Exception, Integer> exceptionHandler) {
		this(action, exceptionHandler, DEFAULT_MAX_ATTEMPTS, null);
	}

	public ActionRepeater(Callable<ActionResultType> action, BiConsumer<Exception, Integer> exceptionHandler, int maxAttempts) {
		this(action, exceptionHandler, maxAttempts, null);
	}

	public ActionRepeater(Callable<ActionResultType> action, int maxAttempts, Duration delay) {
		this(action, DEFAULT_EXCEPTION_HANDLER, maxAttempts, delay);
	}

	public ActionRepeater(Callable<ActionResultType> action, int maxAttempts) {
		this(action, DEFAULT_EXCEPTION_HANDLER, maxAttempts, null);
	}

	public ActionRepeater(Callable<ActionResultType> action, Duration delay) {
		this(action, DEFAULT_EXCEPTION_HANDLER, DEFAULT_MAX_ATTEMPTS, delay);
	}

	public ActionRepeater(Callable<ActionResultType> action) {
		this(action, DEFAULT_MAX_ATTEMPTS);
	}

	public ActionResultType run() {
		int attempt = 0;

		while (attempt < this.maxAttempts) {
			attempt++;
			try {
				return this.action.call();
			} catch (Exception e) {
				int attemptsLeft = this.maxAttempts - attempt;
				this.exceptionHandler.accept(e, attemptsLeft);
				if (attemptsLeft <= 0) {
					throw new RuntimeException(
						String.format(
							"ActionRepeater used up all %d attempts and eventually failed with exception: %s",
							this.maxAttempts,
							ExceptionUtils.getMessage(e)
						),
						e
					);
				} else if (this.delay != null) {
					try {
						Thread.sleep(this.delay.toMillis());
					} catch (InterruptedException ie) {
						throw new RuntimeException(
							String.format(
								"ActionRepeater was interrupted when waiting between attempts. Giving up with %d of %d attempts left.",
								attemptsLeft,
								this.maxAttempts
							),
							ie
						);
					}
				}
			}
		}

		throw new RuntimeException("Something went wrong with ActionRepeater - run out of attempts with no exception to throw.");
	}
}
