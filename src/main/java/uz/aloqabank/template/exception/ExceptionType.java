package uz.aloqabank.template.exception;

import org.apache.commons.lang3.StringUtils;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    EXCEPTION("exception"),
    INVALID_API_RESPONSE("invalid.response"),
    SESSION_EXPIRED("session.expired"),
    ACCESS_DENIED("access.denied"),
    CONFLICT("conflict");

   private final String value;

    ExceptionType(String value) {
        this.value = value;
    }

    public String getMessageTemplate(String entityType) {
        return StringUtils.isNotBlank(entityType) ? StringUtils.joinWith(".", entityType, value) : value;
    }
}
