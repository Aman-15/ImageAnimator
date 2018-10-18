package com.amanagarwal.imageanimator.imageanimator.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface ImageOnClickListener {
    void zoomImageFromThumb(final View thumbView, Drawable image, String url);
}
