package jp.gmo.user.controller;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void executeResetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.executeResetPassword(request);
    }

    @PutMapping("/change-password")
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

    @GetMapping("/list-employees")
    public ResponseEntity<PageAndDataResponseData<List<EmployeeDto>>> executeGetListEmployees(
            @RequestParam(name = "employeeName") String employeeName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {

        Pageable paging = PageRequest.of((page - 1), size, Sort.by("create_time").descending());

        return new ResponseEntity<>(userService.executeGetListEmployees(employeeName, email, paging), HttpStatus.OK);
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

    @GetMapping("/getAll-employees")
    public ResponseEntity<List<EmployeeDto>> executeGetAllEmployees() {
        return new ResponseEntity<>(userService.executeGetAllEmployees(), HttpStatus.OK);
    }
}