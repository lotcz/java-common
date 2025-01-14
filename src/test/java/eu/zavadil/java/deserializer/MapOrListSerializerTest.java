package eu.zavadil.java.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MapOrListSerializerTest {

	private static class MyClass {

		@JsonSerialize(using = MapOrListOfStringToArraySerializer.class)
		public final MapOrList<String> property;

		public MyClass(List<String> list) {
			this.property = new MapOrList<>(list);
		}
	}

	@Test
	public void testString() {
		ObjectMapper mapper = new ObjectMapper();
		MyClass myMapOrList = new MyClass(List.of("test1", "test2", "test3"));

		try {
			String jsonString = mapper.writeValueAsString(myMapOrList);
			Assertions.assertEquals("{\"property\":[\"test1\",\"test2\",\"test3\"]}", jsonString);
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
}
