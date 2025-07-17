package uz.banking.finance.exception;

import uz.banking.finance.constants.ResponseStatus;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message, ResponseStatus.BAD_REQUEST);
    }
}
