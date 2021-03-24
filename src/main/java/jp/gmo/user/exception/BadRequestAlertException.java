package jp.gmo.user.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(URI type, String message) {
        super(type, message, Status.BAD_REQUEST);
    }
}
