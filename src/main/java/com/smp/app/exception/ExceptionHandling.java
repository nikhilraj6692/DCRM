package com.smp.app.exception;


import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.util.SMPAppConstants;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This is Generic Exception handling class. This will handle all exception.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandling {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<BasicResponseTO> handleValidationExceptions(Exception ex) {
        /*Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });*/
        log.error(messageSource.getMessage(ex.getMessage(), null, null), ex);
        BasicResponseTO statusResponse = new BasicResponseTO();

        String rawMessage = null;
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException) ex;
            rawMessage = mex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cex = (ConstraintViolationException) ex;
        }

        String errorCode = StringUtils.hasText(rawMessage) ? rawMessage : SMPAppConstants.DEFAULT_INVALID_PARAMETER;
        String message = messageSource.getMessage(errorCode, null, null);
        statusResponse.setMessage(message);
        statusResponse.setCode(errorCode);

        return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<BasicResponseTO> handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);
        BasicResponseTO statusResponse = new BasicResponseTO();

        String errorCode =
            StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.DEFAULT_INVALID_PARAMETER;
        String message =
            StringUtils.hasText(ex.getMessage()) ? messageSource.getMessage(errorCode, ex.getArgs(), ex.getLocale())
                : messageSource.getMessage(errorCode, null, null);
        statusResponse.setMessage(message);
        statusResponse.setCode(errorCode);
        return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<BasicResponseTO> handleInternalServerException(SystemException ex, WebRequest request) {
        BasicResponseTO statusResponse = new BasicResponseTO();

        //just log the error...errors specific to code/config fail should not be exposed to outside world
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);

        String errorCode = SMPAppConstants.INTERNAL_SERVER_ERROR;
        String message = messageSource.getMessage(errorCode, null, null);

        statusResponse.setCode(errorCode);
        statusResponse.setMessage(message);
        return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<BasicResponseTO> handleFileStorageException(FileStorageException ex, WebRequest request) {
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);
        BasicResponseTO statusResponse = new BasicResponseTO();

        String errorCode =
            StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.DEFAULT_INVALID_PARAMETER;
        String message = StringUtils.hasText(ex.getMessage()) ? messageSource.getMessage(errorCode, ex.getArgs(), null)
            : messageSource.getMessage(errorCode, null, null);
        statusResponse.setCode(errorCode);
        statusResponse.setMessage(message);
        return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<BasicResponseTO> handleNoDataFoundException(NoDataFoundException ex, WebRequest request) {
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);
        BasicResponseTO statusResponse = new BasicResponseTO();

        String errorCode = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.DEFAULT_NO_DATA_FOUND;
        String message =
            StringUtils.hasText(ex.getMessage()) ? messageSource.getMessage(errorCode, ex.getArgs(), ex.getLocale())
                : messageSource.getMessage(errorCode, null, null);
        statusResponse.setMessage(message);
        statusResponse.setCode(errorCode);

        return new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<BasicResponseTO> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {

        BasicResponseTO statusResponse = new BasicResponseTO();
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);
        String errorCode = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.FILE_NOT_FOUND;
        String message = StringUtils.hasText(ex.getMessage()) ? messageSource.getMessage(errorCode, ex.getArgs(), null)
            : messageSource.getMessage(errorCode, null, null);
        statusResponse.setMessage(message);
        statusResponse.setCode(errorCode);

        return new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnAuthorizedException.class, AuthenticationException.class})
    public ResponseEntity<BasicResponseTO> handleAuthenticationException(Exception ex, WebRequest request) {
        if (null != ex.getMessage()) {
            log.error(messageSource.getMessage(ex.getMessage(), null, null), ex);
        } else {
            log.error("Exception: ", ex);
        }
        BasicResponseTO statusResponse = new BasicResponseTO();

        String errorCode = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.INVALID_CREDENTIALS;
        String message = messageSource.getMessage(errorCode, null, null);

        statusResponse.setCode(errorCode);
        statusResponse.setMessage(message);
        return new ResponseEntity<>(statusResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<BasicResponseTO> handleForbiddenException(ForbiddenException ex, WebRequest request) {

        BasicResponseTO statusResponse = new BasicResponseTO();
        if (null != ex.getMessage()) {
            log.error(messageSource.getMessage(ex.getMessage(), null, null), ex);
        } else {
            log.error(ex.getMessage(), ex);
        }
        String errorCode = StringUtils.hasText(ex.getMessage()) ? ex.getMessage() : SMPAppConstants.FORBIDDEN;
        String message = messageSource.getMessage(errorCode, null, null);

        statusResponse.setCode(errorCode);
        statusResponse.setMessage(message);
        return new ResponseEntity<>(statusResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BasicResponseTO> handleBusinessException(BusinessException ex, WebRequest request) {
        log.error(messageSource.getMessage(ex.getMessage(), ex.getArgs(), null), ex);
        BasicResponseTO statusResponse = new BasicResponseTO();
        String message =
            StringUtils.hasText(ex.getMessage()) ? messageSource.getMessage(ex.getMessage(), ex.getArgs(), ex.getLocale())
                : ex.getMessage();

        statusResponse.setMessage(message);
        statusResponse.setCode(ex.getMessage());
        return new ResponseEntity<>(statusResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponseTO> handleGenericException(Exception ex, WebRequest request) {
        if (null != ex.getMessage()) {
            log.error(messageSource.getMessage(ex.getMessage(), null, null), ex);
        } else {
            log.error("Exception at {} ", ex);
        }
        BasicResponseTO statusResponse = new BasicResponseTO();

        String errorCode = SMPAppConstants.INTERNAL_SERVER_ERROR;

        statusResponse.setMessage(messageSource.getMessage(errorCode, null, null));
        statusResponse.setCode(errorCode);
        return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
