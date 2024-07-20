package com.siliconandsynapse.aclient.util;

import android.view.View;
import android.widget.AdapterView;

public interface SelectedListener extends AdapterView.OnItemSelectedListener {
    @Override
    public  default void onNothingSelected(AdapterView<?> parent) {

    }
}
