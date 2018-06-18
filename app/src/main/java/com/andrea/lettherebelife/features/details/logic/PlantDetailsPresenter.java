package com.andrea.lettherebelife.features.details.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.common.domain.PlantInfo;
import com.andrea.lettherebelife.features.common.repository.PlantInfoRepository;
import com.andrea.lettherebelife.features.details.PlantDetailsContract;
import com.andrea.lettherebelife.features.plantinfo.ui.PlantInfoActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.andrea.lettherebelife.util.DecodeImageFromFirebase.decodeImageFromFirebaseBase64;

public class PlantDetailsPresenter {

    private final Context context;
    private final PlantInfoRepository plantInfoRepository;

    private CompositeDisposable disposable = new CompositeDisposable();
    private PlantDetailsContract.View view;
    private Plant plant;
    private PlantInfo plantInfo;

    @Inject
    PlantDetailsPresenter(@NonNull Context context,
                          @NonNull PlantInfoRepository plantInfoRepository) {
        this.context = context;
        this.plantInfoRepository = plantInfoRepository;
    }

    public void connectView(@Nullable PlantDetailsContract.View view, @Nullable Bundle extras, @Nullable Bundle savedInstanceState) {
        this.view = view;

        if (extras == null) {
            assert this.view != null;
            this.view.finishActivity();
            return;
        }

        plant = extras.getParcelable("PLANT");

        init();
    }

    private void init() {
        if (view != null) {
            view.setScreenTitle(plant.getName());
            view.showProgressBar();
        }

        configurePlantDetails();

        disposable.add(plantInfoRepository.getPlantInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse));

//        disposable.add(appDatabase.plantInfoDao().loadAllPlantInfo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse));
    }

    public void showPlantInfo() {
        if (view != null) {
            Intent intent = new Intent(context, PlantInfoActivity.class);
            intent.putExtra("PLANT NAME", plantInfo.getName());
            intent.putExtra("PLANT INFO", plantInfo.getId());
            view.navigateToPlantInfo(intent);
        }
    }

    private void handleSuccessfulResponse(List<PlantInfo> plantInfos) {
        for (PlantInfo plantInfo : plantInfos) {
            if (plant.getName().toLowerCase().contains(plantInfo.getName().toLowerCase())) {
                this.plantInfo = plantInfo;

                if (view != null) {
                    view.hideProgressBar();
                    view.showMenu(true, "About " + plantInfo.getName());
                }
                return;
            }
        }

        if (view != null) {
            view.hideProgressBar();
            view.showMenu(false, "");
        }
    }

    private void handleErrorResponse(Throwable throwable) {
        if (view != null) {
            view.hideProgressBar();
        }
    }

    private void configurePlantDetails() {
        if (view != null) {
            view.renderSeedDate(plant.getSeedDate());
            view.renderDescription(plant.getDescription());
        }

        String photoUrl = plant.getPhotoUrl();
        if (photoUrl != null) {
            if (photoUrl.isEmpty()) {
                view.displayPlantImage(photoUrl);
                return;
            }

            if (!photoUrl.contains("http")) {
                if (view != null) {
                    view.displayPlantImageBitmap(decodeImageFromFirebaseBase64(photoUrl));
                }
            } else {
                if (view != null) {
                    view.displayPlantImage(photoUrl);
                }
            }
        }
    }
}
