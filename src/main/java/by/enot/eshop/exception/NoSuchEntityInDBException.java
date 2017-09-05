package by.enot.eshop.exception;

public class NoSuchEntityInDBException extends Exception {

    public NoSuchEntityInDBException(String message) {
        super(message);
    }

    public NoSuchEntityInDBException() {
    }
}
