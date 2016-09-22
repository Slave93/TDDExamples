package rs.slavko.examples.tdd.managers.messagerouter.exception;

public class UnsupportedMessageTypeException extends RuntimeException {
	public UnsupportedMessageTypeException(String message){
		super(message);
	}
}
