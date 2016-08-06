package eu.carrade.amaury.instantupload.utils;

import android.text.Editable;
import android.text.TextWatcher;


/**
 * A text watcher requiring only the onTextChanged method.
 */
public abstract class SimpleTextWatcher implements TextWatcher
{
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable editable) {}
}
