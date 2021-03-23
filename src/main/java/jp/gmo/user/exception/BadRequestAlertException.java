package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
    }
}
