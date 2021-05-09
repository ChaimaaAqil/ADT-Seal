package com.adria.adriaseal.dto.response.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiValidationError implements ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationError(String object, String field, String message) {
        this.object = object;
        this.message = message;
        this.field = field;
    }

}
