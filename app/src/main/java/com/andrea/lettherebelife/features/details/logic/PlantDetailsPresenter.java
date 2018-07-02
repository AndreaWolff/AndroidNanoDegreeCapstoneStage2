package com.andrea.lettherebelife.features.details.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.data.PlantDao;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.common.domain.PlantInfo;
import com.andrea.lettherebelife.features.details.PlantDetailsContract;
import com.andrea.lettherebelife.features.plantinfo.ui.PlantInfoActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT;
import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT_INFO;
import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT_NAME;
import static com.andrea.lettherebelife.util.DecodeImageFromFirebase.decodeImageFromFirebaseBase64;

public class PlantDetailsPresenter {

    private final Context context;
    private final PlantDao plantDao;

    private CompositeDisposable disposable = new CompositeDisposable();
    private PlantDetailsContract.View view;
    private Plant plant;
    private PlantInfo plantInfo;

    @Inject
    PlantDetailsPresenter(@NonNull Context context,
                          @NonNull PlantDao plantDao) {
        this.context = context;
        this.plantDao = plantDao;
    }

    public void connectView(@Nullable PlantDetailsContract.View view, @Nullable Bundle extras, @Nullable Bundle savedInstanceState) {
        this.view = view;

        if (extras == null) {
            assert this.view != null;
            view.finishActivity();
            return;
        }

        plant = extras.getParcelable(PLANT);

        init();
    }

    private void init() {
        if (view != null) {
            view.setScreenTitle(plant.getName());
            view.showProgressBar();
        }

        configurePlantDetails();
        configureAboutPlantMenu();
    }

    private void configureAboutPlantMenu() {
        disposable.add(plantDao.loadAllPlantInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessfulResponse, this::handleErrorResponse));
    }

    public void showPlantInfo() {
        if (view != null) {
            Intent intent = new Intent(context, PlantInfoActivity.class);
            intent.putExtra(PLANT_NAME, plantInfo.getName());
            intent.putExtra(PLANT_INFO, plantInfo.getId());
            view.navigateToPlantInfo(intent);
        }
    }

    private void handleSuccessfulResponse(List<PlantInfo> plantInfos) {
        for (PlantInfo plantInfo : plantInfos) {
            if (plant.getName().toLowerCase().contains(plantInfo.getName().toLowerCase())) {
                this.plantInfo = plantInfo;

                if (view != null) {
                    view.hideProgressBar();
                    view.showMenu(true, context.getString(R.string.plant_details_title, plantInfo.getName()));
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

    public void onShareSelected() {
        if (plant != null) {
            if (view != null) {
                view.sharePlantDetails("text/plain", context.getString(R.string.share_plant_details, plant.getName(), plant.getSeedDate(), plant.getDescription()));
            }
        }
    }
}
