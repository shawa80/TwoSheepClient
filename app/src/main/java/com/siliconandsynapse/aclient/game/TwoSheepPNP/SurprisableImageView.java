package com.siliconandsynapse.aclient.game.TwoSheepPNP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.siliconandsynapse.aclient.R;

@SuppressLint("AppCompatCustomView")
public class SurprisableImageView extends ImageView {

    private boolean isSupressed = false;

    private Drawable bottom;
    private Drawable surpressed;

    public SurprisableImageView(Context context) {
        super(context);
    }

    public SurprisableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SurprisableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSurpressedImage(Drawable surpressed) {
        this.surpressed = surpressed;
    }

    public void setSupressed(boolean isSupressed) {
        this.isSupressed = isSupressed;

        if (surpressed == null || bottom == null)
            return;

        var d = this.getDrawable();
        var layer = (LayerDrawable) d;

        if (isSupressed) {
            layer.setDrawableByLayerId(R.id.bottom, surpressed);
        } else {
            layer.setDrawableByLayerId(R.id.bottom, bottom);
        }

    }

    public void setImage(int level, Drawable image) {

        var d = this.getDrawable();
        var layer = (LayerDrawable) d;

        bottom = image;

        if (isSupressed)
            image = surpressed;

        layer.setDrawableByLayerId(R.id.bottom, image);
    }

    public void setRemovedImage(int level, Drawable image) {

        var d = this.getDrawable();
        var layer = (LayerDrawable) d;

        bottom = image;
        surpressed = image;

        layer.setDrawableByLayerId(R.id.bottom, image);
    }
}
