package jp.gmo.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.request.*;
import jp.gmo.user.response.data.PageAndDataResponseData;
import jp.gmo.user.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.response.ResponseCommon;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.ResponseUtils;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/get-account")
    public ResponseEntity<AccountDto> executeGetAccountInfo(HttpServletRequest request, @Param("email") String email){
        log.debug("REQUEST method=[{}] path=[{}] parameters=[{}]", request.getMethod(), request.getRequestURI(), email);
        return ResponseUtil.wrapOrNotFound(userService.executeGetInfoAccount(email).map(AccountDto::new));
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void executeResetPassword(@Valid @RequestBody ResetPasswordRequest request){
        userService.executeResetPassword(request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void executeChangePassword(@RequestHeader("email") String email, @Valid @RequestBody ChangePasswordRequest request){
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
}