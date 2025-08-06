package com.siliconandsynapse.aclient;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

public class InsertHandler implements OnApplyWindowInsetsListener {
    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat windowInsets) {
        var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        var cutout = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout());

        var mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        mlp.leftMargin = insets.left + cutout.left;
        mlp.bottomMargin = insets.bottom + cutout.left;
        mlp.rightMargin = insets.right + cutout.right;
        mlp.topMargin = insets.top + cutout.top;
        v.setLayoutParams(mlp);

        return WindowInsetsCompat.CONSUMED;
    }
}
