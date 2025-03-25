package uz.aloqabank.template.exception;


import uz.aloqabank.template.constants.ResponseStatus;

public class SessionExpiredException extends ApiException {
    public SessionExpiredException(String message) {
        super(message, ResponseStatus.UNAUTHORIZED);
    }
}
