package jp.gmo.user.interceptor;

import jp.gmo.user.service.LoggingService;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * The Class CustomRequestBodyAdviceAdapter.
 */
@ControllerAdvice
@AllArgsConstructor
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final LoggingService loggingService;
    private final HttpServletRequest httpServletRequest;

    /**
     * Supports.
     *
     * @param methodParameter the method parameter
     * @param type            the type
     * @param aClass          the a class
     * @return true, if successful
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull  Type type,
                            @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * After body read.
     *
     * @param body          the body
     * @param inputMessage  the input message
     * @param parameter     the parameter
     * @param targetType    the target type
     * @param converterType the converter type
     * @return the object
     */
    @Override
    @NonNull public Object afterBodyRead(@NonNull Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        loggingService.logRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
