package com.andrea.lettherebelife.features.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.Plant;

import java.util.List;

public interface MainContract {
    interface View {
        void showPlantList(@NonNull List<Plant> plantList);

        void showNoPlant(@NonNull String noPlant);

        void navigateToNewPlantActivity(@NonNull Intent intent);

        void navigateToPlantDetailsActivity(@NonNull Intent intent);
    }
}
