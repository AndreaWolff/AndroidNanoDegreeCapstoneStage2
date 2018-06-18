package com.andrea.lettherebelife.dagger.component;

import android.content.Context;

import com.andrea.lettherebelife.dagger.module.AppModule;
import com.andrea.lettherebelife.dagger.module.NetModule;
import com.andrea.lettherebelife.features.common.repository.PlantInfoRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Context getContext();

    PlantInfoRepository getPlantInfoRepository();
}
