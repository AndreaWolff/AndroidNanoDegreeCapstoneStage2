package com.andrea.lettherebelife.features.main.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.details.ui.PlantDetailsActivity;
import com.andrea.lettherebelife.features.main.MainContract;
import com.andrea.lettherebelife.features.newplant.ui.NewPlantActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import javax.inject.Inject;

public class MainPresenter {

    private final Context context;

    private MainContract.View view;

    @Inject MainPresenter(@Nullable Context context) {
        this.context = context;
    }

    public void connectView(@Nullable MainContract.View view) {
        this.view = view;
    }

    public void setPlantList(@Nullable List<Plant> plantList) {
        if (plantList != null) {
            if (view != null) {
                view.showPlantList(plantList);
            }
        }
    }

    public void onNewPlantSelected() {
        Intent intent = new Intent(context, NewPlantActivity.class);

        if (view != null) {
            view.navigateToNewPlantActivity(intent);
        }
    }

    public void onPlantListItemSelected(@NonNull Plant plant) {
        Intent intent = new Intent(context, PlantDetailsActivity.class);
        intent.putExtra("PLANT", plant);

        if (view != null) {
            view.navigateToPlantDetailsActivity(intent);
        }
    }

    public void detachDatabaseReadListener(@Nullable ValueEventListener valueEventListener) {
        if (valueEventListener != null) {
            if (view != null) {
                view.detachValueEventListener();
            }
        }
    }

    public void disconnectView() {
        view = null;
    }
}

