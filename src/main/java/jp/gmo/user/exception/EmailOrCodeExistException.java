package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EmailOrCodeExistException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public EmailOrCodeExistException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageConstants.CONST_MSG_VALIDATE_EMAIL_OR_CODE, Status.BAD_REQUEST);
    }
}
