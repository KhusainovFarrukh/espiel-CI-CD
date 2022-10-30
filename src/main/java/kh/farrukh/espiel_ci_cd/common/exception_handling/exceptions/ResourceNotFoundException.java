package kh.farrukh.espiel_ci_cd.common.exception_handling.exceptions;

import kh.farrukh.espiel_ci_cd.common.exception_handling.ApiException;
import kh.farrukh.espiel_ci_cd.common.exception_handling.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends ApiException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue),
                HttpStatus.NOT_FOUND,
                ExceptionMessages.RESOURCE_NOT_FOUND,
                new Object[]{resourceName, fieldName, fieldValue}
        );
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
