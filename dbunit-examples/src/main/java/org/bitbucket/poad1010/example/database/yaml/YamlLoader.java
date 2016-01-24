package org.bitbucket.poad1010.example.database.yaml;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlLoader {
	private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

	public static <T> T load(String file, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
		return MAPPER.readValue(YamlLoader.class.getResource(new StringBuilder("/").append(file).toString()), type);
	}
	public static <T extends Collection<V>, V> T load(String file, TypeReference<T> type) throws JsonParseException, JsonMappingException, IOException {
		return MAPPER.readValue(YamlLoader.class.getResourceAsStream(new StringBuilder("/").append(file).toString()), type);
	}
}
