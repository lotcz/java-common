package eu.zavadil.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class ActionRepeaterTest {

	private class ActionAlwaysSucceedMock implements Callable<String> {

		public int valueFetched = 0;

		@Override
		public String call() {
			this.valueFetched++;
			return "OK";
		}
	}

	private class ActionAlwaysFailMock implements Callable<String> {

		public int valueFetched = 0;

		@Override
		public String call() {
			this.valueFetched++;
			throw new RuntimeException("Fail!");
		}
	}

	private class ActionSucceedSecondTimeMock implements Callable<String> {

		public int valueFetched = 0;

		@Override
		public String call() {
			this.valueFetched++;
			if (this.valueFetched == 1) throw new RuntimeException("Fail!");
			return "OK";
		}
	}

	@Test
	public void testActionRepeaterSimple() {
		ActionRepeater<String> ar = new ActionRepeater<>(() -> "Hello");

		String result = null;
		boolean failed = false;

		try {
			result = ar.run();
		} catch (Exception e) {
			failed = true;
		}

		Assertions.assertFalse(failed);
		Assertions.assertEquals("Hello", result);
	}

	@Test
	public void testActionRepeaterSuccess() {
		ActionAlwaysSucceedMock actionSucceed = new ActionAlwaysSucceedMock();
		ActionRepeater<String> ar = new ActionRepeater<>(actionSucceed);

		String result = null;
		boolean failed = false;

		try {
			result = ar.run();
		} catch (Exception e) {
			failed = true;
		}

		Assertions.assertFalse(failed);
		Assertions.assertEquals("OK", result);
		Assertions.assertEquals(1, actionSucceed.valueFetched);
	}

	@Test
	public void testActionRepeaterFail() {
		ActionAlwaysFailMock actionFail = new ActionAlwaysFailMock();
		ActionRepeater<String> ar = new ActionRepeater<>(actionFail);

		String result = null;
		boolean failed = false;

		try {
			result = ar.run();
		} catch (Exception e) {
			failed = true;
		}

		Assertions.assertNull(result);
		Assertions.assertTrue(failed);
		Assertions.assertEquals(ActionRepeater.DEFAULT_MAX_ATTEMPTS, actionFail.valueFetched);
	}

	@Test
	public void testActionRepeaterSecond() {
		ActionSucceedSecondTimeMock actionSecond = new ActionSucceedSecondTimeMock();
		ActionRepeater<String> ar = new ActionRepeater<>(actionSecond);

		String result = null;
		boolean failed = false;

		try {
			result = ar.run();
		} catch (Exception e) {
			failed = true;
		}

		Assertions.assertFalse(failed);
		Assertions.assertEquals("OK", result);
		Assertions.assertEquals(2, actionSecond.valueFetched);
	}
}
