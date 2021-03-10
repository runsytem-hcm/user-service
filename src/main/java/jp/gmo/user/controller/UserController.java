package jp.gmo.user.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * The Class UserController.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
}
