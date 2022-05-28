package navi.vehicle.rental.lld.exceptions;

public class BranchNotFoundException extends Exception {
    public BranchNotFoundException() {
        super();
    }

    public BranchNotFoundException(String message) {
        super(message);
    }

    public BranchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BranchNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BranchNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
