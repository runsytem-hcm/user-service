package jp.gmo.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseDto {
    @JsonProperty(value = "data", index = 1)
    private Object result;

    @JsonProperty(value = "message", index = 2)
    private String message;

    @JsonProperty(value = "errors", index = 3)
    private List<FieldErrorDto> errors;

}

