package eu.zavadil.java.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.zavadil.java.Lazy;

import java.util.function.Supplier;

public class JsonUtils {

	public static ObjectMapper createObjectMapper() {
		return new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static Supplier<ObjectMapper> defaultMapper = new Lazy<>(JsonUtils::createObjectMapper);

	public static <T> String toJson(T object) {
		try {
			return defaultMapper.get().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Error when converting object to JSON.", e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (StringUtils.isEmpty(json)) return null;
		try {
			return defaultMapper.get().readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Error when converting JSON to object.", e);
		}
	}

	public static <T> T fromJson(String json, TypeReference<T> type) {
		try {
			return defaultMapper.get().readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException("Error when converting JSON to object.", e);
		}
	}

}
