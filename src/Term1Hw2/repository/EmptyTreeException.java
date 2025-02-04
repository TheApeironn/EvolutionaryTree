package Term1Hw2.repository;

public class EmptyTreeException extends RuntimeException {
	public EmptyTreeException() {
		this(null);
	} // end default constructor

	public EmptyTreeException(String message) {
		super(message);
	} // end constructor
} // end EmptyTreeException
