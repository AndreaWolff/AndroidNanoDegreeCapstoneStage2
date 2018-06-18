package com.andrea.lettherebelife.features.main.logic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.details.ui.PlantDetailsActivity;
import com.andrea.lettherebelife.features.main.MainContract;
import com.andrea.lettherebelife.features.newplant.ui.NewPlantActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter {

    private final Context context;
    private final DatabaseReference messagesDatabaseReference;

    private MainContract.View view;
    private ValueEventListener valueEventListener;
    private List<Plant> plantList;

    @Inject
    MainPresenter(@NonNull Context context,
                  @NonNull DatabaseReference messagesDatabaseReference) {
        this.context = context;
        this.messagesDatabaseReference = messagesDatabaseReference;
    }

    public void connectView(@Nullable MainContract.View view) {
        this.view = view;

        init();
    }

    private void init() {
        attachValueEventListener();
    }

    public void onPause() {
        if (valueEventListener != null) {
            messagesDatabaseReference.removeEventListener(valueEventListener);
            this.valueEventListener = null;
        }
    }

    public void disconnectView() {
        view = null;
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

    private void attachValueEventListener() {
        plantList = new ArrayList<>();

        if (valueEventListener == null) {
            messagesDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    plantList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Plant plant = snapshot.getValue(Plant.class);
                        plantList.add(plant);
                    }

                    if (plantList != null) {
                        if (view != null) {
                            view.showPlantList(plantList);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

