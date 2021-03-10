package jp.gmo.user.controller;

import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.dto.request.LoginRequestDto;
import jp.gmo.user.service.UserService;
import jp.gmo.user.utils.ResponseUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * The Class UserController.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> executeAuthorize(@Valid @RequestBody LoginRequestDto request){
        return new ResponseEntity<>(ResponseUtils.success(userService.executeAuthorize(request), MessageConstants.CONST_MSG_NORMAL), HttpStatus.OK);
    }
}
