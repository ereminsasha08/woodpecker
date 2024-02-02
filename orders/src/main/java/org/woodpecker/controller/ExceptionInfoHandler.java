package org.woodpecker.controller;

import org.woodpecker.util.validation.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.woodpecker.util.exception.*;

import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    public static final String EXCEPTION_DUPLICATE_DATETIME = "exception.meal.duplicateDateTime";

    private static final Map<String, String> CONSTRAINTS_I18N_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "meals_unique_user_datetime_idx", EXCEPTION_DUPLICATE_DATETIME);

//    private final MessageSourceAccessor messageSourceAccessor;


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorInfo> updateRestrictionError(HttpServletRequest req, ApplicationException appEx) {
        return logAndGetErrorInfo(req, appEx, false, appEx.getType(), new String(appEx.getMsgCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINTS_I18N_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, false, ErrorType.VALIDATION_ERROR, new String(entry.getValue()));
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, ErrorType.DATA_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> bindValidationError(HttpServletRequest req, BindException e) {
        String[] details = e.getBindingResult().getFieldErrors().stream().map(FieldError::getField).toArray(String[]::new);
        return logAndGetErrorInfo(req, e, false, ErrorType.VALIDATION_ERROR, details);
    }

    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> validationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.VALIDATION_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logStackTrace, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logStackTrace, errorType);
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(req.getRequestURL(), errorType,
                        new String(errorType.getErrorCode()),
                        details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)})
                );
    }
}