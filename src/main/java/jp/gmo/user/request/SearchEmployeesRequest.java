package jp.gmo.user.request;

import jp.gmo.user.constant.MessageConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchEmployeesRequest {

    private String employeeName;
    private String email;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    private String currentPage;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    private String totalRecordOfPage;
}
