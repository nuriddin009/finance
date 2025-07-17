package uz.banking.finance.exception;

import lombok.Getter;
import uz.banking.finance.constants.ResponseStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ResponseStatus status;

    public ApiException(String message, ResponseStatus status) {
        super(message);
        this.status = status;
    }

}
