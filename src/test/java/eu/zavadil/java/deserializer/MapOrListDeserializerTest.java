package eu.zavadil.java.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapOrListDeserializerTest {

	private static class MyClass {

		@JsonDeserialize(using = MapOrListOfStringDeserializer.class)
		public MapOrList<String> property;

	}

	@Test
	public void testString() {
		String jsonMapString = "{ \"property\": { \"key1\": \"value1\", \"key2\": \"value2\" } }";
		String jsonArrayString = "{ \"property\": [ \"value1\", \"value2\" ] }";

		ObjectMapper mapper = new ObjectMapper();

		try {
			MyClass myMap = mapper.readValue(jsonMapString, MyClass.class);
			MyClass myArray = mapper.readValue(jsonArrayString, MyClass.class);

			Assertions.assertNotNull(myMap.property);
			Assertions.assertNotNull(myArray.property);

			Assertions.assertTrue(myMap.property.isMap());
			Assertions.assertTrue(myArray.property.isList());

			Assertions.assertFalse(myMap.property.isList());
			Assertions.assertFalse(myArray.property.isMap());

			Assertions.assertNotNull(myMap.property.asMap());
			Assertions.assertNotNull(myArray.property.asList());

			Assertions.assertFalse(myMap.property.asMap().isEmpty());
			Assertions.assertFalse(myArray.property.asList().isEmpty());

			Assertions.assertTrue(myMap.property.asMap().containsKey("key1"));
			Assertions.assertTrue(myMap.property.asMap().containsKey("key2"));
			Assertions.assertTrue(myArray.property.asList().contains("value1"));
			Assertions.assertTrue(myArray.property.asList().contains("value2"));

		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
}
