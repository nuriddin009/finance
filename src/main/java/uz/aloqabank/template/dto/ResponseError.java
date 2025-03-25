package uz.aloqabank.template.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import uz.aloqabank.template.utils.RequestIdUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseError {
    private String requestId;
    private LocalDateTime timestamp;
    private String message;
    private Object details;

    public ResponseError() {
        this.requestId = RequestIdUtils.getRequestId();
        timestamp = LocalDateTime.now();
    }
}
