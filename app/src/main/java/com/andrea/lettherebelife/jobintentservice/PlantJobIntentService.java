package com.andrea.lettherebelife.jobintentservice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.andrea.lettherebelife.application.PlantApplication;
import com.andrea.lettherebelife.data.AppExecutor;
import com.andrea.lettherebelife.features.common.domain.PlantInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static com.andrea.lettherebelife.features.common.ActivityConstants.JOB_ID;

public class PlantJobIntentService extends JobIntentService {

    private final CompositeDisposable disposable = new CompositeDisposable();

    public static void enqueueWork(@NonNull Context context) {
        enqueueWork(context, PlantJobIntentService.class, JOB_ID, new Intent());
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        disposable.add(PlantApplication.getDagger().getPlantInfoRepository().getPlantInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlePlantInfoResponseSuccessful, this::handleResponseError));
    }

    private void handlePlantInfoResponseSuccessful(List<PlantInfo> plantInfos) {
        AppExecutor.getInstance().diskIO().execute(() -> PlantApplication.getDagger().getPlantDao().bulkInsertPlantInfo(plantInfos));
    }

    private void handleResponseError(Throwable throwable) {
        // Fails silently
        if (throwable.getMessage() != null) {
            Log.d("TAG", throwable.getMessage());
        }
    }
}
