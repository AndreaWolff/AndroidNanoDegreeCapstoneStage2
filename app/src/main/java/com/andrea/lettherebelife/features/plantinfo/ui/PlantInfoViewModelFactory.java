package com.andrea.lettherebelife.features.plantinfo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class PlantInfoViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    PlantInfoViewModelFactory() {}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.equals(PlantInfoViewModel.class)) {
            //noinspection unchecked
            return (T) new PlantInfoViewModel();
        }

        throw new IllegalArgumentException("Unsupported view model type");
    }
}
