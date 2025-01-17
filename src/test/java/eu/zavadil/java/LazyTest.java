package eu.zavadil.java;

import eu.zavadil.java.caching.Lazy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class LazyTest {

	private class SupplierMock implements Supplier<String> {

		public int valueFetched = 0;

		@Override
		public String get() {
			this.valueFetched++;
			return "value";
		}
	}

	@Test
	public void testLazySupplier() {
		SupplierMock supplier = new SupplierMock();
		Supplier<String> lazy = new Lazy<>(supplier);

		Assertions.assertEquals(0, supplier.valueFetched, "No value fetching before value is requested");

		String value = lazy.get();

		Assertions.assertEquals("value", value, "Value must match mocked string");
		Assertions.assertEquals(1, supplier.valueFetched, "Value was requested and fetched once");

		String value2 = lazy.get();

		Assertions.assertEquals("value", value2, "Value must match mocked string");
		Assertions.assertEquals(1, supplier.valueFetched, "Value was requested twice, but should have been only fetched once");
	}
}
