package uz.banking.finance.exception;


import uz.banking.finance.constants.ResponseStatus;

public interface ExceptionHandler {

    /**
     * Throw API exceptions
     *
     * @param message
     * @param status
     */
    default void exception(String message, ResponseStatus status) {
        throw new ApiException(message, status);
    }

    /**
     * Throw exception by entity and proper exception type
     *
     * @param entityType
     * @param exceptionType
     * @param args
     */
    default void exception(String entityType, ExceptionType exceptionType, String... args) {
        throw new ApiExceptionManager().throwException(entityType, exceptionType, args);
    }
}
