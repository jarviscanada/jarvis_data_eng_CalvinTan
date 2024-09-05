package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.util.LoggerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

public class JsonParser {

    private final static Logger logger = LoggerUtil.getLogger();

    public static <T> T toObjectFromJson(String json, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(json);
            jsonNode = jsonNode.get("Global Quote");
            if (jsonNode == null) throw new RuntimeException();
            T obj = (T) mapper.readValue(jsonNode.toString(), clazz);
            return obj;
        } catch (JsonProcessingException e) {
            logger.error("ERROR: failed to parse json string", e);
        }
        return null;
    }
}
