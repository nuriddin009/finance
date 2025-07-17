package uz.banking.finance.constants;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    //success
    OK(200, "Ok"),

    //4xx Client Error
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    SERVER_ERROR(404, "Not Found"),

    TOKEN_EXPIRED(450, "Token expired"),
    TOKEN_BLOCKED(451, "Unavailable For Legal Reasons"),
    SMS_PIN_EXPIRED(453, "SMS pin expired"),

    NOT_VALID(470, "Not Valid"),
    WRONG_CREDENTIALS(471, "Wrong Credentials"),

    //5xx Server Error
    ACCESS_DENIED(570, "Access Denied"),
    DUPLICATE_ENTITY(571, "Duplicate Entity"),

    EXCEPTION(591, "Exception");

    final int statusCode;
    final String statusName;

    ResponseStatus(int code, String title) {
        this.statusCode = code;
        this.statusName = title;
    }

}
