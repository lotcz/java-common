package eu.zavadil.java.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class JsonUtilTest {

	private static class ObjectMock {

		public int intValue;

		public BigDecimal bigDecimalValue;

		public String stringValue;

		public Instant instantValue;

		public List<String> listValue;

		public boolean equals(ObjectMock other) {
			if (other == null) return false;
			return other.intValue == this.intValue
				&& StringUtils.safeEquals(other.stringValue, this.stringValue)
				&& BigDecimalUtils.safeEquals(other.bigDecimalValue, this.bigDecimalValue)
				&& InstantUtils.safeEquals(other.instantValue, this.instantValue)
				&& ((other.listValue == null && this.listValue == null)
					|| (other.listValue != null && other.listValue.equals(this.listValue))
				);
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof ObjectMock)) {
				return false;
			}
			return equals((ObjectMock) other);
		}
	}

	@Test
	public void testToJson() {
		ObjectMock object = new ObjectMock();
		object.intValue = 0;
		object.bigDecimalValue = new BigDecimal("1.2345");
		object.stringValue = "test";
		object.listValue = Arrays.asList("item1", "item2");
		object.instantValue = InstantUtils.safeParseIso("2024-04-27T10:05:08.123Z");
		String json = JsonUtils.toJson(object);
		Assertions.assertEquals(
			"{\"intValue\":0,\"bigDecimalValue\":1.2345,\"stringValue\":\"test\",\"instantValue\":\"2024-04-27T10:05:08.123Z\",\"listValue\":[\"item1\",\"item2\"]}",
			json,
			"JSON string must match"
		);
	}

	@Test
	public void testFromJson() {
		String json = """
			{
				"intValue": 1983,
				"bigDecimalValue": 3.14,
				"stringValue": "Hello",
				"instantValue": "2024-04-27T10:05:08.123Z",
				"listValue": ["item1", "item2"]
			}
			""";
		ObjectMock deser = JsonUtils.fromJson(json, ObjectMock.class);
		Assertions.assertNotNull(deser, "No object returned");
		Assertions.assertEquals(1983, deser.intValue);
		Assertions.assertEquals(BigDecimal.valueOf(3.14), deser.bigDecimalValue);
		Assertions.assertEquals("Hello", deser.stringValue);
		Assertions.assertEquals(InstantUtils.safeParseIso("2024-04-27T10:05:08.123Z"), deser.instantValue);
	}

	@Test
	public void testFromAndToJson() {
		ObjectMock object = new ObjectMock();
		object.intValue = 0;
		object.bigDecimalValue = new BigDecimal("1.2345");
		object.stringValue = "test";
		object.listValue = Arrays.asList("item1", "item2");
		object.instantValue = Instant.now();

		String json = JsonUtils.toJson(object);
		ObjectMock deser = JsonUtils.fromJson(json, ObjectMock.class);
		Assertions.assertEquals(object, deser, "Deserialized object must be identical with the original");
	}

}
