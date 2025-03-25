package uz.aloqabank.template.exception;

import uz.aloqabank.template.constants.ResponseStatus;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message, ResponseStatus.BAD_REQUEST);
    }
}
