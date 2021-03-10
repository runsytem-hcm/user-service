package jp.gmo.user.utils;

import jp.gmo.user.dto.FieldErrorDto;
import jp.gmo.user.dto.ResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtils {

    public static Map<String, Object> success(Object data, String message) {
        Map<String, Object> response = new HashMap<>();

        ResponseDto res = new ResponseDto();
        res.setResult(data);
        res.setMessage(message);
        response.put("response", res);

        return response;
    }

    public static ResponseDto errors(String message) {
        ResponseDto res = new ResponseDto();
        res.setMessage(message);
        return res;
    }

    public static ResponseDto errors(List<FieldErrorDto> data) {
        ResponseDto res = new ResponseDto();
        res.setErrors(data);
        return res;
    }
}
