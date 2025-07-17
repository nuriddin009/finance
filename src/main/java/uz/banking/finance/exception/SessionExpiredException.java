package uz.banking.finance.exception;


import uz.banking.finance.constants.ResponseStatus;

public class SessionExpiredException extends ApiException {
    public SessionExpiredException(String message) {
        super(message, ResponseStatus.UNAUTHORIZED);
    }
}
