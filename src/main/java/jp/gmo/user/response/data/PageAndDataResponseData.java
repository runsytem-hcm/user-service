package jp.gmo.user.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageAndDataResponseData {

    private Object list;
    private String totalRecord;
    private String currentPage;
    private String totalRecordOfPage;
}
