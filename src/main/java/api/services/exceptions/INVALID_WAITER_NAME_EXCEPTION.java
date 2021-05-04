package api.services.exceptions;

public class INVALID_WAITER_NAME_EXCEPTION extends Exception{
    public INVALID_WAITER_NAME_EXCEPTION(String message) {
        super("Waiter name is invalid.");
    }
}

