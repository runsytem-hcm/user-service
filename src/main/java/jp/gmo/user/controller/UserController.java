package jp.gmo.user.controller;

import javax.validation.Valid;

import jp.gmo.user.request.*;
import jp.gmo.user.response.ResponseCommonTest;
import jp.gmo.user.response.data.AccountResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.response.ResponseCommon;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.ResponseUtils;
import jp.gmo.user.utils.Utils;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/get-account-info")
    public ResponseEntity<ResponseCommonTest<AccountResponseData>> executeGetAccountInfo(@Valid @RequestBody LoginRequest request){
        return new ResponseEntity<>(ResponseCommonTest.create(userService.executeGetInfoAccount(request), "", LocalDateTime.now()), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseCommon> executeResetPassword(@Valid @RequestBody ResetPasswordRequest request){
        userService.executeResetPassword(request);
        return new ResponseEntity<>(ResponseUtils.success(null, Utils.getMessage(MessageConstants.CONST_MSG_NORMAL)), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseCommon> executeChangePassword(@RequestHeader("email") String email, @Valid @RequestBody ChangePasswordRequest request){
        userService.executeChangePassword(email, request);
        return new ResponseEntity<>(ResponseUtils.success(null, Utils.getMessage(MessageConstants.CONST_MSG_NORMAL)), HttpStatus.OK);
    }

    @PostMapping("/create-account")
    public ResponseEntity<ResponseCommon> executeCreateAccount(@Valid @RequestBody CreateAccountRequest request) {
        userService.executeCreateAccount(request);
        return new ResponseEntity<>(ResponseUtils.success(null, Utils.getMessage(MessageConstants.CONST_MSG_NORMAL)), HttpStatus.CREATED);
    }

    @PostMapping("/add-employees")
    public ResponseEntity<ResponseCommon> executeAddEmployees(@Valid @RequestBody AddEmployeesRequest request) {
        userService.executeAddEmployees(request);
        return new ResponseEntity<>(ResponseUtils.success(null, Utils.getMessage(MessageConstants.CONST_MSG_NORMAL)), HttpStatus.CREATED);
    }

    @PostMapping("/list-employees")
    public ResponseEntity<ResponseCommon> executeGetListEmployees(@Valid @RequestBody SearchEmployeesRequest request) {
        return new ResponseEntity<>(ResponseUtils.success(userService.executeGetListEmployees(request), Utils.getMessage(MessageConstants.CONST_MSG_NORMAL)), HttpStatus.OK);
    }
}
