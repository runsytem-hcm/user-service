package jp.gmo.user.constant;

public class RegexConstants {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9@%+\\/'!#$^?:.(){}\\[\\]~\\-_.]*$";
    public static final String FORMAT_DATE_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
}
