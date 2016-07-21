package com.jaouan.viewsfrom.filters;

/**
 * Filter helper.
 */
final class FilterHelper {

    /**
     * Private constructor to disallow instantiation.
     */
    private FilterHelper() {
    }

    /**
     * Check if array contains a value.
     *
     * @param array Array.
     * @param value Value.
     * @return TRUE if array contains value.
     */
    static <TypeOfValue> boolean arrayContains(final TypeOfValue[] array, final TypeOfValue value) {
        for (final TypeOfValue arrayValue : array) {
            if (arrayValue == value || (arrayValue != null && arrayValue.equals(value))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if array contains a value.
     *
     * @param array Array.
     * @param value Value.
     * @return TRUE if array contains value.
     */
    static boolean arrayContains(final int[] array, final int value) {
        for (final int arrayValue : array) {
            if (arrayValue == value) {
                return true;
            }
        }

        return false;
    }

}
