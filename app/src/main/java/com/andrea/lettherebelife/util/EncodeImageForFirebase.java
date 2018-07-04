package com.andrea.lettherebelife.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class EncodeImageForFirebase {

    // Issues storing photos taken from https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    @NonNull public static String encodeBitmapAndSaveToFirebase(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
