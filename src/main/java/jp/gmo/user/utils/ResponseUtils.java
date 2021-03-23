package jp.gmo.user.utils;

import java.time.LocalDateTime;
import java.util.List;

import jp.gmo.user.exception.FieldError;
import jp.gmo.user.response.ResponseCommon;

public class ResponseUtils {

    public static ResponseCommon success(Object data, String message) {

    	ResponseCommon res = new ResponseCommon();
        res.setResult(data);
        res.setMessage(message);
        res.setTimestamp(LocalDateTime.now());
        return res;
    }

    public static ResponseCommon errors(String message) {

        ResponseCommon res = new ResponseCommon();
        res.setMessage(message);
        res.setTimestamp(LocalDateTime.now());
        return res;
    }

    public static ResponseCommon errors(String message, List<FieldError> errors) {
        ResponseCommon res = new ResponseCommon();
    	res.setMessage(message);
        res.setErrors(errors);
        res.setTimestamp(LocalDateTime.now());
        return res;
    }
}
