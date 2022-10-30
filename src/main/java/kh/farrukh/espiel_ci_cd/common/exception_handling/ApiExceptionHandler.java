package kh.farrukh.espiel_ci_cd.common.exception_handling;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception, Locale locale) {
        exception.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponse(
                        messageSource.getMessage(exception.getMessageId(), exception.getMessageArgs(), locale),
                        exception.getStatus()
                ),
                exception.getStatus()
        );
    }
}
