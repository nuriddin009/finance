package uz.aloqabank.template.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;


public final class JSONUtils {

    public static String toJSON(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return JSONMapper.getInstance().writeValueAsString(object);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        ObjectMapper mapper = JSONMapper.getInstance();

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJSON(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return JSONMapper.getInstance().readValue(json, typeReference);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getHostNameByMacAddress(String macAddress) {
        String hostName = null;
        String[] part = macAddress.split(":");
        if (part.length > 3) {
            hostName = "-" + part[part.length - 3] +
                "-" + part[part.length - 2] +
                "-" + part[part.length - 1];
        }
        return hostName;
    }
}
