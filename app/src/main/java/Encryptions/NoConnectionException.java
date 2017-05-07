package Encryptions;

public class NoConnectionException extends Exception {
	private static final String msg= "No connection established";
	public NoConnectionException() {
		super(msg);
	}
}
