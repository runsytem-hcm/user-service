package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;
import jp.gmo.user.constant.MessageConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.DEFAULT_TYPE, "Email is already in use!");
    }
}
