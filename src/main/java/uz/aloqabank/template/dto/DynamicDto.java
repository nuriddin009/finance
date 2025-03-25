package uz.aloqabank.template.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@ToString
public class DynamicDto implements Serializable {
    /**
     * This one is for "handling Dynamic Field" purpose
     */
    @JsonIgnore
    private Map<String, Object> properties;

    @JsonAnySetter
    public DynamicDto addProperty(String key, Object value) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
        return properties;
    }

}
