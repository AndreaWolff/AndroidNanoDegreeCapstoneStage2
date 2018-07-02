package com.andrea.lettherebelife.features.plantinfo.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.andrea.lettherebelife.application.PlantApplication;
import com.andrea.lettherebelife.features.common.domain.PlantInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT_INFO;

public class PlantInfoViewModel extends ViewModel {

    public MutableLiveData<String> plantDescription = new MutableLiveData<>();
    public MutableLiveData<String> plantOptimalSun = new MutableLiveData<>();
    public MutableLiveData<String> plantConsiderations = new MutableLiveData<>();
    public MutableLiveData<String> plantGrowingFromSeed = new MutableLiveData<>();
    public MutableLiveData<String> plantOtherCare = new MutableLiveData<>();
    public MutableLiveData<String> plantDiseases = new MutableLiveData<>();
    public MutableLiveData<String> plantHarvesting = new MutableLiveData<>();
    public MutableLiveData<String> plantStorageUse = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    private int plantInfoId;

    PlantInfoViewModel() {
    }

    public void setPlantInfoViewModel(@Nullable Bundle extras) {
        if (extras != null) {
            int plantInfoId = extras.getInt(PLANT_INFO);
            if (plantInfoId > 0) {
                this.plantInfoId = plantInfoId;
            }
        }

        configurePlantInfo();
    }

    private void configurePlantInfo() {
        disposable.add(PlantApplication.getDagger().getPlantDao().loadPlantInfoById(plantInfoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse));
    }

    private void handleSuccessfulResponse(PlantInfo info) {
        plantDescription.setValue(info.getDescription());
        plantOptimalSun.setValue(info.getOptimalSun());
        plantConsiderations.setValue(info.getPlantingConsiderations());
        plantGrowingFromSeed.setValue(info.getGrowingFromSeed());
        plantOtherCare.setValue(info.getOtherCare());
        plantDiseases.setValue(info.getDiseases());
        plantHarvesting.setValue(info.getHarvesting());
        plantStorageUse.setValue(info.getStorageUse());
    }

    private void handleErrorResponse(Throwable throwable) {
        Log.d("TAG", throwable.getMessage());
        Toast.makeText(PlantApplication.getDagger().getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
