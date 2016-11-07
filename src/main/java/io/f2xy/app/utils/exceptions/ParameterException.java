package io.f2xy.app.utils.exceptions;

/**
 * Date: 25.03.2014
 *
 * @author Hakan GÜR
 * @version 1.0
 */
public class ParameterException extends ApplicationException {

    private String parameterName;
    private String invalidValue;

    public ParameterException(String parameterName, String invalidValue, Throwable cause) {
        super(cause);
        this.parameterName = parameterName;
        this.invalidValue = invalidValue;
    }

    public ParameterException(String parameterName, String invalidValue) {
        this.parameterName = parameterName;
        this.invalidValue = invalidValue;
    }

}
