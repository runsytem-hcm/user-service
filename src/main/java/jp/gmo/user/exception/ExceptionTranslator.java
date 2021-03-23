package jp.gmo.user.exception;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.web.util.HeaderUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.core.env.Environment;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Environment env;

    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
            .withStatus(problem.getStatus())
            .withTitle(problem.getTitle())
            .with(PATH_KEY, Objects.requireNonNull(request.getNativeRequest(HttpServletRequest.class)).getRequestURI());

        builder
                .withDetail(problem.getDetail())
                .withInstance(problem.getInstance());
        problem.getParameters().forEach(builder::with);

        if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
            builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
        }

        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors().stream()
            .map(f -> new FieldError(f.getField(), f.getDefaultMessage()))
            .collect(Collectors.toList());

        Problem problem = Problem.builder()
            .withTitle("Method argument not valid")
            .withStatus(defaultConstraintViolationStatus())
            .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex, NativeWebRequest request) {
        EmailAlreadyUsedException problem = new EmailAlreadyUsedException();
        return create(problem, request, HeaderUtil.createFailureAlert(applicationName,  false, problem.getEntityName(), problem.getErrorKey(), problem.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUsernameAlreadyUsedException(UsernameAlreadyUsedException ex, NativeWebRequest request) {
        LoginAlreadyUsedException problem = new LoginAlreadyUsedException();
        return create(problem, request, HeaderUtil.createFailureAlert(applicationName,  false, problem.getEntityName(), problem.getErrorKey(), problem.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleInvalidPasswordException(InvalidPasswordException ex, NativeWebRequest request) {
        return create(new InvalidPasswordException(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
        return create(ex, request, new HttpHeaders());
    }
}