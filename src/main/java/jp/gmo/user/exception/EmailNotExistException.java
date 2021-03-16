package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EmailNotExistException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public EmailNotExistException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageConstants.CONST_MSG_VALIDATE_EMAIL_NOT_EXIST, Status.BAD_REQUEST);
    }
}
