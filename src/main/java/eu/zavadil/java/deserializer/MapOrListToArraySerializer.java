package eu.zavadil.java.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.List;

public class MapOrListToArraySerializer<ValueType> extends JsonSerializer<MapOrList<ValueType>> {

	@Override
	public void serialize(MapOrList<ValueType> valueTypeMapOrList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (valueTypeMapOrList == null) {
			jsonGenerator.writeNull();
		} else {
			List<String> list = (valueTypeMapOrList.isMap()) ?
				valueTypeMapOrList.asMap().keySet().stream().map(Object::toString).toList()
				: valueTypeMapOrList.asList().stream().map(Object::toString).toList();
			String[] arr = new String[list.size()];
			list.toArray(arr);
			jsonGenerator.writeArray(arr, 0, arr.length);
		}
	}
}
