package jp.gmo.user.service;

import jp.gmo.user.request.ChangePasswordRequest;
import jp.gmo.user.request.CreateAccountRequest;
import jp.gmo.user.request.LoginRequest;
import jp.gmo.user.request.ResetPasswordRequest;
import jp.gmo.user.response.data.AccountResponseData;

public interface UserService {
    AccountResponseData executeGetInfoAccount(LoginRequest request);
    void executeResetPassword(ResetPasswordRequest request);
    void executeChangePassword(String email, ChangePasswordRequest request);
    void executeCreateAccount(CreateAccountRequest request);
}
