package jp.gmo.user.exception;

import jp.gmo.user.constant.ErrorConstants;

public class EmailNotExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailNotExistException() {
        super(ErrorConstants.DEFAULT_TYPE, "Email is already in use!");
    }
}
