package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CustomUsernameNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public CustomUsernameNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageConstants.CONST_ERROR_EMAIL_PASSWORD_NOT_MATCH, Status.UNAUTHORIZED);
    }
}
