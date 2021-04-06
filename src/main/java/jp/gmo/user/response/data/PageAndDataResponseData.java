package jp.gmo.user.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
@AllArgsConstructor
public class PageAndDataResponseData<T> {

    @JsonProperty(value = "data", index = 1)
    private T list;
    private long totalRecord;
    private int currentPage;
    private int totalRecordOfPage;

    public static <T> PageAndDataResponseData<T> create(T list, long totalRecord, int currentPage, int totalRecordOfPage) {
        return new PageAndDataResponseData<T>(list, totalRecord, currentPage, totalRecordOfPage);
    }
}
