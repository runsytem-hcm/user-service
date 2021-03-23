package jp.gmo.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ResponseCommonTest<T> {
    @JsonProperty(value = "data", index = 1)
    private T result;

    @JsonProperty(value = "message", index = 2)
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    public static <T> ResponseCommonTest<T> create(T data, String message, LocalDateTime timestamp) {
        return new ResponseCommonTest<T>(data, message, timestamp);
    }
}

