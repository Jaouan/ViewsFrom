package com.jaouan.viewsfrom;

/**
 * Utils for function.
 */
final class FunctionUtils {


    /**
     * Private constructor to disallow instantiation.
     */
    private FunctionUtils() {
    }

    /**
     * Checks that an array does not contain null reference.
     *
     * @param parameterName   Parameter name.
     * @param parameterValues Parameter values.
     * @param <ParameterType> Parameter type.
     */
    static <ParameterType> void checkParameterArrayIsNotNull(final String parameterName, final ParameterType[] parameterValues) {
        // - Check array.
        checkParameterIsNotNull(parameterName, parameterValues);

        // - Check array size.
        if (parameterValues.length == 0) {
            throw new IllegalArgumentException(parameterName + " cannot be empty.");
        }

        // - Check array values.
        for (final ParameterType parameterValue : parameterValues) {
            if (parameterValue == null) {
                throw new IllegalArgumentException(parameterName + " cannot contain null.");
            }
        }
    }

    /**
     * Checks that an array does not contain null reference.
     *
     * @param parameterName   Parameter name.
     * @param parameterValues Parameter values.
     */
    static void checkParameterArrayIsNotNull(final String parameterName, final int[] parameterValues) {
        // - Check array.
        checkParameterIsNotNull(parameterName, parameterValues);

        // - Check array size.
        if (parameterValues.length == 0) {
            throw new IllegalArgumentException(parameterName + " cannot be empty.");
        }
    }

    /**
     * Checks that a parameter is not null.
     *
     * @param parameterName  Parameter name.
     * @param parameterValue Parameter value.
     */
    static void checkParameterIsNotNull(final String parameterName, final Object parameterValue) {
        // - Check array.
        if (parameterValue == null) {
            throw new IllegalArgumentException(parameterName + " cannot be null.");
        }
    }

}
