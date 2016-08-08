package eu.carrade.amaury.instantupload.utils;


import android.support.design.widget.TextInputLayout;


public final class MaterialUtils
{
    private MaterialUtils() {}

    /**
     * Sets an error on a Material Design TextInput layout.
     *
     * @param inputLayout The layout
     * @param error The error, to enable it; {@code null} to remove the error.
     */
    public static void setInputError(TextInputLayout inputLayout, CharSequence error)
    {
        inputLayout.setError(error);
        inputLayout.setErrorEnabled(error != null);
    }

    /**
     * Sets an error on a Material Design TextInput layout if the condition is satisfied.
     *
     * @param inputLayout The layout.
     * @param condition The condition to check.
     * @param error The error to display, if the condition is satisfied.
     */
    public static void setInputError(TextInputLayout inputLayout, boolean condition, CharSequence error)
    {
        setInputError(inputLayout, condition ? error : null);
    }
}
