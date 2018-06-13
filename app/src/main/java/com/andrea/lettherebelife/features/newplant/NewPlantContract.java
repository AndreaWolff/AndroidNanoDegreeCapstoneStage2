package com.andrea.lettherebelife.features.newplant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.Plant;

public interface NewPlantContract {
    interface View {
        void setScreenTitle(@NonNull String title);

        void enableSaveButton(boolean saveVisibility);

        void enterDataIntoDatabase(@NonNull Plant plant);

        void navigateToAddImage(@NonNull Intent intent, int image_id);

        void renderPlantImage(@NonNull Bitmap imageBitmap);
    }
}
