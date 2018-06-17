package com.andrea.lettherebelife.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.andrea.lettherebelife.R;
import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void displayImage(@NonNull String photo, @NonNull ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(photo)
                .placeholder(R.color.colorAccent)
                .into(imageView);
    }

    public static void displayPlantImage(@NonNull String photo, @NonNull ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(photo)
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .into(imageView);
    }
}