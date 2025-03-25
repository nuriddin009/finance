package uz.aloqabank.template.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.aloqabank.template.dto.Response;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiExceptionManager.EntityNotFoundException.class)
    public final ResponseEntity<?> handleNotFountExceptions(Exception ex) {
        Response<Object> response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<?> handleApiException(ApiException ex) {
        Response<Object> response = Response.exception(ex.getStatus());
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        int statusCode = ex.getStatus().getStatusCode();
        return new ResponseEntity<>(response, HttpStatus.resolve(statusCode) != null ? HttpStatus.valueOf(statusCode) : HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<?> handleRuntimeException(Exception ex) {
        Response<Object> response = Response.serverError();
        response.addErrorMsgToResponse(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Oops something went wrong. Please try again later.", ex);
        log.error("{}", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SessionExpiredException.class)
    public final ResponseEntity<?> handleSessionExpiredException(Exception ex) {
        Response<Object> response = Response.unauthorized();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        Response<Object> response = Response.badRequest();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        Response<Object> response = Response.badRequest();
        response.addErrorMsgToResponse(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Required fields are missing or invalid", errorList);
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }
}
