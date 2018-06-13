package com.andrea.lettherebelife.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andrea.lettherebelife.application.PlantApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final PlantApplication application;

    public AppModule(@NonNull PlantApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context context() {
        return application.getApplicationContext();
    }
}
