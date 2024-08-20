package ca.jrvs.apps.stockquote.json;

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
            T obj = (T) mapper.readValue(jsonNode.toString(), clazz);
            logger.debug("DEBUG: class:" + obj.getClass());
            return obj;
        } catch (JsonProcessingException e) {
            logger.error("ERROR: failed to parse json string", e);
        }
        return null;
    }
}
