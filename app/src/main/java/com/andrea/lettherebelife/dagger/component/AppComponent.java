package com.andrea.lettherebelife.dagger.component;

import android.content.Context;

import com.andrea.lettherebelife.dagger.module.AppModule;
import com.andrea.lettherebelife.dagger.module.NetModule;
import com.andrea.lettherebelife.data.AppDatabase;
import com.andrea.lettherebelife.data.PlantDao;
import com.andrea.lettherebelife.features.common.repository.PlantInfoRepository;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Context getContext();

    PlantInfoRepository getPlantInfoRepository();

    PlantDao getPlantDao();

    DatabaseReference getFirebaseDatabaseReference();
}
