package uz.aloqabank.template.exception;

import lombok.Getter;
import uz.aloqabank.template.constants.ResponseStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ResponseStatus status;

    public ApiException(String message, ResponseStatus status) {
        super(message);
        this.status = status;
    }

}
