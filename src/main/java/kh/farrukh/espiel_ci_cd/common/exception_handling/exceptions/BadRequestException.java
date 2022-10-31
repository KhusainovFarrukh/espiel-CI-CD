package kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions;

import kh.farrukh.espiel_ci_cd.common.exception_handling.ApiException;
import kh.farrukh.espiel_ci_cd.common.exception_handling.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends ApiException {

    private final String fieldName;

    public BadRequestException(String fieldName) {
        super(
                String.format("%s is not valid", fieldName),
                HttpStatus.BAD_REQUEST,
                ExceptionMessages.RESOURCE_NOT_FOUND,
                new Object[]{fieldName}
        );
        this.fieldName = fieldName;
    }
}
