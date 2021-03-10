/*	
 * Aviva SMS Revamp
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: GiangTT
 * Creation Date : Oct 15, 2019
 */
package jp.gmo.user.exception;

import java.util.List;
import java.util.stream.Collectors;


import jp.gmo.user.constant.Constants;
import jp.gmo.user.dto.FieldErrorDto;
import jp.gmo.user.utils.ResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;


@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {


    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        ProblemBuilder builder = Problem.builder();
        builder.with(Constants.CONST_RESPONSE, ResponseUtils.errors(problem != null ? problem.getTitle() : null));
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorDto> fieldErrors = result.getFieldErrors().stream()
            .map(f -> new FieldErrorDto( f.getField(), f.getDefaultMessage()))
            .collect(Collectors.toList());

        ProblemBuilder builder = Problem.builder();
        builder.with(Constants.CONST_RESPONSE, ResponseUtils.errors(fieldErrors));
        return new ResponseEntity<>(builder.build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}