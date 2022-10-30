package kh.farrukh.espiel_ci_cd.common.exception_handling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String messageId;
    private final Object[] messageArgs;
    private final HttpStatus status;

    public ApiException(String message, Throwable cause, HttpStatus status, String messageId, Object[] messageArgs) {
        super(message, cause);
        this.status = status;
        this.messageId = messageId;
        this.messageArgs = messageArgs;
    }

    public ApiException(String message, HttpStatus status, String messageId, Object[] messageArgs) {
        super(message);
        this.status = status;
        this.messageId = messageId;
        this.messageArgs = messageArgs;
    }
}
