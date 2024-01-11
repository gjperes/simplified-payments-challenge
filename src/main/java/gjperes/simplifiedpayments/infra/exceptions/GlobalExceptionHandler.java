package gjperes.simplifiedpayments.infra.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String API_DEFAULT_ERROR_MESSAGE = "There was something wrong with the operation. Please contact our support team.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.info(ex.getMessage(), ex);

        var errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(err -> ApiErrorDetails.builder()
                        .pointer(((FieldError) err).getField())
                        .reason(err.getDefaultMessage())
                        .build()
                )
                .toList();

        return ResponseEntity.badRequest().body(buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation failed", errors));
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatusCode status,
                                                                            WebRequest request) {
        log.info(ex.getMessage(), ex);

        List<ApiErrorDetails> errors = new ArrayList<>();

        ex.getAllValidationResults().forEach(validation -> {
            var parameterName = validation.getMethodParameter().getParameterName();

            var details = validation.getResolvableErrors()
                    .stream()
                    .map(err -> ApiErrorDetails.builder()
                            .pointer(parameterName)
                            .reason(err.getDefaultMessage())
                            .build()
                    ).toList();

            errors.addAll(details);
        });

        return ResponseEntity.badRequest().body(buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation failed", errors));
    }

    // Fallback, catch all unknown API exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleAllExceptions(Throwable ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, API_DEFAULT_ERROR_MESSAGE);
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String detail) {
        return buildProblemDetail(status, detail, Collections.emptyList());
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String description, List<ApiErrorDetails> errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, description);
        problemDetail.setProperty("timestamp", ZonedDateTime.now());

        if (!errors.isEmpty()) {
            problemDetail.setProperty("errors", errors);
        }

        return problemDetail;
    }
}
