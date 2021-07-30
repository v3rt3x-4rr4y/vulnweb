package com.jukka.vulnweb.web.exception;

public class UserIdMismatchException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

    public UserIdMismatchException()
    {
        super();
    }

    public UserIdMismatchException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public UserIdMismatchException(final String message)
    {
        super(message);
    }

    public UserIdMismatchException(final Throwable cause)
    {
        super(cause);
    }
}
