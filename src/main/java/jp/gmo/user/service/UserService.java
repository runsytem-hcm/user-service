package jp.gmo.user.service;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AccountDto> executeGetInfoAccount(String email);

    void executeResetPassword(ResetPasswordRequest request);

    void executeChangePassword(String email, ChangePasswordRequest request);

    void executeCreateAccount(CreateAccountRequest request);

    void executeAddEmployees(AddEmployeesRequest request);

    PageAndDataResponseData<List<EmployeeDto>> executeGetListEmployees(String employeeName, String email, Pageable paging);

    Optional<EmployeeDto> executeGetDetailEmployee(String employeeCode);

    void executeUpdateEmployees(UpdateEmployeesRequest request);

    List<EmployeeDto> executeGetAllEmployees();
}
