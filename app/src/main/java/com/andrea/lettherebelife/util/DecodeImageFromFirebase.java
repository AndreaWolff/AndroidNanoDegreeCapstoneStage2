package com.andrea.lettherebelife.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import static android.graphics.BitmapFactory.decodeByteArray;
import static android.util.Base64.DEFAULT;

public class DecodeImageFromFirebase {

    public static Bitmap decodeImageFromFirebaseBase64(@NonNull String image) {
        byte[] decodedByteArray = android.util.Base64.decode(image, DEFAULT);
        return decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}
