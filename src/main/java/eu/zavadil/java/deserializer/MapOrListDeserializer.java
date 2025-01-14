package eu.zavadil.java.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.zavadil.java.util.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapOrListDeserializer<ValueType> extends JsonDeserializer<MapOrList<ValueType>> {

    private ObjectMapper mapper = JsonUtils.defaultMapper.get();

    @Override
    public MapOrList<ValueType> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isNull()) return null;

        if (node.isObject()) {
            Map<String, ValueType> object = mapper.convertValue(node, Map.class);
            return new MapOrList<>(object);
        } else if (node.isArray()) {
            List<ValueType> array = mapper.convertValue(node, List.class);
            return new MapOrList<>(array);
        } else {
            throw new IOException(String.format("Unexpected JSON type when parsing '%s'", p.currentName()));
        }
    }
}
