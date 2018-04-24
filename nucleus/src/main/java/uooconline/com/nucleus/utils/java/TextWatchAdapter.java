package uooconline.com.nucleus.utils.java;

import android.text.Editable;
import android.text.TextWatcher;


public abstract class TextWatchAdapter implements TextWatcher {
    public abstract void onChange(String text);

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    final public void afterTextChanged(Editable s) {
        onChange(s.toString());
    }
}
