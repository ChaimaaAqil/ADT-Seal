package com.adria.adriaseal.exception;

import com.adria.adriaseal.dto.response.error.ApiError;
import com.adria.adriaseal.dto.response.error.ApiValidationError;
import eu.europa.esig.dss.alert.exception.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import java.nio.file.FileSystemNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, FileNotValidException.class,
            HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleConstraintViolation(Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setDebugMessage(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\\n")));
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = ((ConstraintViolationException) exception);
            apiError.setSubErrors(
                    violationException.getConstraintViolations().stream()
                            .map(constraintViolation ->
                                    new ApiValidationError(exception.getMessage().split("\\.")[0],
                                            constraintViolation.getPropertyPath().toString(),
                                            constraintViolation.getInvalidValue(),
                                            constraintViolation.getMessage()))
                            .collect(Collectors.toList())
            );
            apiError.setMessage(violationException.getLocalizedMessage());
            apiError.setDebugMessage(violationException.getMessage());
        }
        if (exception.getCause() != null)
            apiError.setDebugMessage(exception.getCause().getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ValidationException.class, MissingServletRequestPartException.class,
            BindException.class
            , MethodArgumentNotValidException.class
    })
    protected ResponseEntity<Object> handleBindException(Exception exception) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("There were some validation errors !");
        if (exception instanceof ValidationException) {
            apiError.addValidationErrors(((ValidationException) exception).getFieldErrors());
        }
        if (exception instanceof BindException) {
            apiError.addValidationErrors(((BindException) exception).getFieldErrors());
            apiError.setDebugMessage(((BindException) exception).getBindingResult().toString());
        } else if (exception instanceof MissingServletRequestPartException) {
            final String requestPartName = ((MissingServletRequestPartException) exception).getRequestPartName();
            apiError.setSubErrors(
                    Collections.singletonList(new ApiValidationError("Missing Servlet Request Part", requestPartName, exception.getLocalizedMessage()))
            );
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MultipartFileException.class)
    protected ResponseEntity<Object> handleMultipartFileException(MultipartFileException exception) {
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "There was a problem extracting files!", exception);
        return buildResponseEntity(apiError);
    }

    // todo change this to resource not found exception
    @ExceptionHandler(FileSystemNotFoundException.class)
    protected ResponseEntity<Object> handleFileSystemNotFoundException(FileSystemNotFoundException exception) {
        ApiError apiError = new ApiError(HttpStatus.GONE, "File is gone!", exception);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        ApiError apiError = new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, "Maximum upload size exceeded!", exception);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(OutOfMemoryError.class)
    protected ResponseEntity<Object> handleOutOfMemoryError(OutOfMemoryError exception) {
        ApiError apiError = new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, "Couldn't process the file at the moment!", exception);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({GenericException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleGenericException(Exception exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getCause().getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AlertException.class)
    protected ResponseEntity<Object> handleAlertException(AlertException exception) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception);
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}
