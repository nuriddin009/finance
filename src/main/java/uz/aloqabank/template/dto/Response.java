package uz.aloqabank.template.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import uz.aloqabank.template.constants.ResponseStatus;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    @JsonProperty("status")
    private String statusName;
    private int statusCode;
    private T payload;
    private Object errors;

    public static <T> Response<T> badRequest() {
        return new Response<T>()
                .setStatus(ResponseStatus.BAD_REQUEST);
    }

    public static <T> Response<T> serverError() {
        return new Response<T>()
                .setStatus(ResponseStatus.SERVER_ERROR);
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.OK);
        return response;
    }

    public static <T> Response<T> unauthorized() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.UNAUTHORIZED);
        return response;
    }

    public static <T> Response<T> notValid() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.NOT_VALID);
        return response;
    }

    public static <T> Response<T> wrongCredentials() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.WRONG_CREDENTIALS);
        return response;
    }

    public static <T> Response<T> accessDenied() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.ACCESS_DENIED);
        return response;
    }

    public static <T> Response<T> exception() {
        return exception(ResponseStatus.EXCEPTION);
    }

    public static <T> Response<T> exception(ResponseStatus status) {
        Response<T> response = new Response<>();
        response.setStatus(status);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.NOT_FOUND);
        return response;
    }

    public static <T> Response<T> duplicateEntity() {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatus.DUPLICATE_ENTITY);
        return response;
    }

    public void addErrorMsgToResponse(String message, Object ex) {
        ResponseError error;
        error = new ResponseError()
                .setMessage(message);

        if (ex instanceof Exception) {
            error.setDetails(((Exception) ex).getMessage());
        } else {
            error.setDetails(ex);
        }
        setErrors(error);
    }

    Response<T> setStatus(ResponseStatus status) {
        this.setStatusCode(status.getStatusCode());
        this.setStatusName(status.getStatusName());
        return this;
    }
}

