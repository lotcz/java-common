package eu.zavadil.java;

import eu.zavadil.java.caching.Cached;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Supplier;

public class CachedTest {

	private class SupplierMock implements Supplier<String> {

		public int valueFetched = 0;

		@Override
		public String get() {
			this.valueFetched++;
			return "value";
		}
	}

	@Test
	public void testCachedSupplier() throws InterruptedException {
		SupplierMock supplier = new SupplierMock();
		Duration lifetime = Duration.ofMillis(200);
		Supplier<String> cached = new Cached<>(lifetime, supplier);

		Assertions.assertEquals(0, supplier.valueFetched, "No value fetching before value is requested");

		String value = cached.get();

		Assertions.assertEquals("value", value, "Value must match mocked string");
		Assertions.assertEquals(1, supplier.valueFetched, "Value was requested and fetched once");

		String value2 = cached.get();

		Assertions.assertEquals("value", value2, "Value must match mocked string");
		Assertions.assertEquals(1, supplier.valueFetched, "Value must not be fetched before expiration");

		Thread.sleep(lifetime.toMillis());

		String value3 = cached.get();

		Assertions.assertEquals("value", value3, "Value must match mocked string");
		Assertions.assertEquals(2, supplier.valueFetched, "Value must be fetched again once it expires");

		String value4 = cached.get();

		Assertions.assertEquals("value", value4, "Value must match mocked string");
		Assertions.assertEquals(2, supplier.valueFetched, "Value must not be fetched before expiration");

		Thread.sleep(lifetime.toMillis());

		String value5 = cached.get();

		Assertions.assertEquals("value", value5, "Value must match mocked string");
		Assertions.assertEquals(3, supplier.valueFetched, "Value must be fetched again once it expires");

	}
}
