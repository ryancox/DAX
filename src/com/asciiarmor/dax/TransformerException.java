package com.asciiarmor.dax;


/**
 * 
 * Wrapper for exceptions thrown in DAX Transformer.
 * <b>NOTE:</b> This extends RuntimeException. Exceptions are unchecked!
 * 
 */
public class TransformerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5708729717517046784L;

	public TransformerException(Exception e) {
		super(e);
	}
}
