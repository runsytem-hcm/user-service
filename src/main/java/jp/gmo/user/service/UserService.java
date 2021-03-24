package jp.gmo.user.service;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.AccountResponseData;
import jp.gmo.user.response.data.PageAndDataResponseData;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AccountDto> executeGetInfoAccount(String email);

    void executeResetPassword(ResetPasswordRequest request);

    void executeChangePassword(String email, ChangePasswordRequest request);

    void executeCreateAccount(CreateAccountRequest request);

    void executeAddEmployees(AddEmployeesRequest request);

    PageAndDataResponseData<List<EmployeeDto>> executeGetListEmployees(SearchEmployeesRequest request);
}
