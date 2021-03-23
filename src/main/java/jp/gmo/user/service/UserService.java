package jp.gmo.user.service;

import jp.gmo.user.request.*;
import jp.gmo.user.response.data.AccountResponseData;
import jp.gmo.user.response.data.PageAndDataResponseData;

public interface UserService {
    AccountResponseData executeGetInfoAccount(LoginRequest request);

    void executeResetPassword(ResetPasswordRequest request);

    void executeChangePassword(String email, ChangePasswordRequest request);

    void executeCreateAccount(CreateAccountRequest request);

    void executeAddEmployees(AddEmployeesRequest request);

    PageAndDataResponseData executeGetListEmployees(SearchEmployeesRequest request);
}
