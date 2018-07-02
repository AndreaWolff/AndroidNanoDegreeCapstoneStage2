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

    // Taken from https://stackoverflow.com/questions/33222918/sharing-bitmap-via-android-intent
//    public static Uri saveImage(Bitmap image) {
//        File imagesFolder = new File(PlantApplication.getDagger().getContext().getCacheDir(), "images");
//        Uri uri = null;
//
//        try {
//            imagesFolder.mkdirs();
//            File file = new File(imagesFolder, "shared_image.png");
//
//            FileOutputStream stream = new FileOutputStream(file);
//            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
//            stream.flush();
//            stream.close();
//            uri = FileProvider.getUriForFile(PlantApplication.getDagger().getContext(), "com.mydomain.fileprovider", file);
//        } catch (IOException e) {
//
//        }
//
//        return uri;
//    }
}
