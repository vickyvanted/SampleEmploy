
package com.employee.smacs.service.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ResourceNotFoundException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ResourceNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ResourceNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ResourceNotFoundException(Throwable arg0) {
		super(arg0);
	}
}
