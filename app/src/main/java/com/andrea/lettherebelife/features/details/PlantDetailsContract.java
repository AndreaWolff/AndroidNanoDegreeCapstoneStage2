package com.andrea.lettherebelife.features.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public interface PlantDetailsContract {
    interface View {
        void setScreenTitle(@NonNull String title);

        void renderSeedDate(@NonNull String seedDate);

        void renderDescription(@NonNull String description);

        void displayPlantImageBitmap(@NonNull Bitmap image);

        void displayPlantImage(@NonNull String photoUrl);

        void showProgressBar();

        void hideProgressBar();

        void showMenu(boolean menuVisibility, @NonNull String menuTitle);

        void navigateToPlantInfo(@NonNull Intent intent);

        void finishActivity();
    }
}
