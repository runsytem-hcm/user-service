package jp.gmo.user.controller;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get-account")
    public ResponseEntity<AccountDto> executeGetAccountInfo(@Param("email") String email) {
        return ResponseUtil.wrapOrNotFound(userService.executeGetInfoAccount(email).map(AccountDto::new));
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void executeResetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.executeResetPassword(request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void executeChangePassword(@RequestHeader("email") String email, @Valid @RequestBody ChangePasswordRequest request) {
        userService.executeChangePassword(email, request);
    }

    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public void executeCreateAccount(@Valid @RequestBody CreateAccountRequest request) {
        userService.executeCreateAccount(request);
    }

    @PostMapping("/add-employees")
    @ResponseStatus(HttpStatus.CREATED)
    public void executeAddEmployees(@Valid @RequestBody AddEmployeesRequest request) {
        userService.executeAddEmployees(request);
    }

    @PostMapping("/list-employees")
    public ResponseEntity<PageAndDataResponseData<List<EmployeeDto>>> executeGetListEmployees(@Valid @RequestBody SearchEmployeesRequest request) {
        return new ResponseEntity<>(userService.executeGetListEmployees(request), HttpStatus.OK);
    }

    @GetMapping("/detail-employees")
    public ResponseEntity<EmployeeDto> executeGetDetailEmployee(@Param("employeeCode") String employeeCode) {
        return ResponseUtil.wrapOrNotFound(userService.executeGetDetailEmployee(employeeCode)
                .map(employeeDto -> new EmployeeDto(employeeDto.getEmployeeCode(), employeeDto.getEmployeeName(), employeeDto.getEmail())));
    }

    @PutMapping("/update-employees")
    @ResponseStatus(HttpStatus.OK)
    public void executeUpdateEmployee(@Valid @RequestBody UpdateEmployeesRequest request) {
        userService.executeUpdateEmployees(request);
    }
}