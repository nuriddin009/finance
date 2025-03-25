package uz.aloqabank.template.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import uz.aloqabank.template.config.ApplicationContextProvider;
import uz.aloqabank.template.constants.ResponseStatus;

import java.util.Locale;

@Slf4j
@Component
@DependsOn("applicationContextProvider")
public class ApiExceptionManager {

    private static MessageSource messageSource;

    public ApiExceptionManager() {
        if (ApiExceptionManager.messageSource == null) {
            ApiExceptionManager.messageSource = ApplicationContextProvider.applicationContext.getBean("messageSource", MessageSource.class);
        }
    }

    public RuntimeException throwApiException(String message, ResponseStatus status) {
        return new ApiException(message, status);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    public RuntimeException throwException(String messageTemplate, String... args) {
        return new RuntimeException(format(messageTemplate, args));
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public RuntimeException throwException(String entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = exceptionType.getMessageTemplate(entityType);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public RuntimeException throwExceptionWithId(String entityType, ExceptionType exceptionType, String id, String... args) {
        String messageTemplate = exceptionType.getMessageTemplate(entityType).concat(".").concat(id);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
     *
     * @param exceptionType
     * @param messageTemplate
     * @param args
     * @return
     */
    public RuntimeException throwExceptionWithTemplate(ExceptionType exceptionType, String messageTemplate, String... args) {
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(messageTemplate, args));
        } else if (ExceptionType.SESSION_EXPIRED.equals(exceptionType)) {
            return new SessionExpiredException(format(messageTemplate, args));
        } else if (ExceptionType.ACCESS_DENIED.equals(exceptionType)) {
            return new AccessDeniedException(format(messageTemplate, args));
        }
        return new RuntimeException(format(messageTemplate, args));
    }

    String format(String template, String... args) {
        String templateContent = null;
        try {
            templateContent = messageSource.getMessage(template, args, Locale.US);
        } catch (NoSuchMessageException e) {
            log.error("NoSuchMessageException: {}", e);
        }

        if (StringUtils.isNotBlank(templateContent)) {
            return templateContent;
        }
        return String.format(template, args);
    }


    public static class EntityNotFoundException extends ApiException {
        public EntityNotFoundException(String message) {
            super(message, ResponseStatus.NOT_FOUND);
        }
    }

    public static class AccessDeniedException extends ApiException {
        public AccessDeniedException(String message) {
            super(message, ResponseStatus.ACCESS_DENIED);
        }
    }
}
