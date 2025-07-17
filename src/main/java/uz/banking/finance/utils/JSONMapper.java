package uz.banking.finance.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONMapper extends ObjectMapper {
    private volatile static JSONMapper instance;

    private JSONMapper() {
        if (instance != null) {
            throw new UnsupportedOperationException("This is a singleton class, so create object from its constructor is not supported!");
        }
    }

    public static JSONMapper getInstance() {
        if (instance == null) {
            synchronized (JSONMapper.class) {
                if (instance == null) {
                    instance = new JSONMapper();
                    instance.findAndRegisterModules();
                    instance.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                    instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                }
            }
        }
        return instance;
    }
}
