package com.andrea.lettherebelife.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.andrea.lettherebelife.data.AppDatabase;
import com.andrea.lettherebelife.data.PlantDao;
import com.andrea.lettherebelife.features.common.repository.PlantInfoDao;
import com.andrea.lettherebelife.features.common.repository.PlantInfoRepository;
import com.andrea.lettherebelife.features.common.repository.PlantInfoRepositoryDefault;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private final String BASE_URL;
    private final AppDatabase appDatabase;

    public NetModule(@NonNull String BASE_URL, @NonNull Application application) {
        this.BASE_URL = BASE_URL;
        appDatabase = Room.databaseBuilder(application, AppDatabase.class,  "lettherebelife").build();
    }

    @Singleton
    @Provides
    OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Singleton
    @Provides
    Retrofit retrofit(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    PlantInfoDao plantInfoDao(@NonNull Retrofit retrofit) {
        return retrofit.create(PlantInfoDao.class);
    }

    @Singleton
    @Provides
    PlantInfoRepository plantInfoRepository(@NonNull PlantInfoRepositoryDefault impl) {
        return impl;
    }

    @Singleton
    @Provides
    FirebaseDatabase getFirebaseDatabaseInstance() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    DatabaseReference getFirebaseDatabaseReference(@NonNull FirebaseDatabase firebaseDatabase) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("plants");
        databaseReference.keepSynced(true);
        return databaseReference;
    }

    @Singleton
    @Provides
    AppDatabase appDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    PlantDao plantDao() {
        return appDatabase.plantDao();
    }
}
