package be.jimmyd.cm.domain.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String s) {
        super(s);
    }
}
