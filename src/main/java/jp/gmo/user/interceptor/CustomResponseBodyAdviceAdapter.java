package jp.gmo.user.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jp.gmo.user.service.LoggingService;

/**
 * The Class CustomResponseBodyAdviceAdapter.
 */
@ControllerAdvice
@AllArgsConstructor
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    private final LoggingService loggingService;

    /**
     * Supports.
     *
     * @param methodParameter the method parameter
     * @param aClass          the a class
     * @return true, if successful
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * Before body write.
     *
     * @param o                  the o
     * @param methodParameter    the method parameter
     * @param mediaType          the media type
     * @param aClass             the a class
     * @param serverHttpRequest  the server http request
     * @param serverHttpResponse the server http response
     * @return the object
     */
    @Override
    public Object beforeBodyWrite(Object o, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest
                && serverHttpResponse instanceof ServletServerHttpResponse) {
            loggingService.logResponse(((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }

        return o;
    }
}
