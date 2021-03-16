package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UpdatePasswordException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public UpdatePasswordException() {
        super(ErrorConstants.DEFAULT_TYPE, MessageConstants.CONST_ERROR_UPDATE_PASSWORD, Status.INTERNAL_SERVER_ERROR);
    }
}
