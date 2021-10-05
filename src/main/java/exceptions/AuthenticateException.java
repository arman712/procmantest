package exceptions;

public class AuthenticateException extends RuntimeException {
    public AuthenticateException() {
        super("Not authenticated");
    }
}
