package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidPasswordException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super(ErrorConstants.DEFAULT_TYPE, "Incorrect password", Status.BAD_REQUEST);
    }
}
