package com.andrea.lettherebelife.application;

import android.app.Application;

import com.andrea.lettherebelife.dagger.component.AppComponent;
import com.andrea.lettherebelife.dagger.component.DaggerAppComponent;
import com.andrea.lettherebelife.dagger.module.AppModule;
import com.andrea.lettherebelife.dagger.module.NetModule;
import com.facebook.stetho.Stetho;
import com.google.firebase.database.FirebaseDatabase;

public class PlantApplication extends Application {

    private static PlantApplication application;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        appComponent = createDaggerComponent();

        Stetho.initializeWithDefaults(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private AppComponent createDaggerComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://harvesthelper.herokuapp.com/api/v1/", this))
                .build();
    }

    public static AppComponent getDagger() {
        return application.appComponent;
    }

}
