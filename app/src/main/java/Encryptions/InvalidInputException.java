package Encryptions;

class InvalidInputException extends Exception {
	private static final String exceptionMsg = "Invalid key or text";
	public InvalidInputException() {
		super(exceptionMsg);
	}
}
