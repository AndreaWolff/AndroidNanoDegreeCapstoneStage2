package com.andrea.lettherebelife.features.main.logic;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.details.ui.PlantDetailsActivity;
import com.andrea.lettherebelife.features.main.MainContract;
import com.andrea.lettherebelife.features.newplant.ui.NewPlantActivity;
import com.andrea.lettherebelife.widget.PlantWidget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;
import static android.content.Context.MODE_PRIVATE;
import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT;
import static com.andrea.lettherebelife.features.common.ActivityConstants.SHARED_PREFERENCES;
import static com.andrea.lettherebelife.features.common.ActivityConstants.WIDGET;

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
        intent.putExtra(PLANT, plant);

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

                        configurePlantListWidget(plantList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        checkDatabaseConnection();
    }

    private void checkDatabaseConnection() {
        // Issue checking for internet connection taken from https://firebase.google.com/docs/database/android/offline-capabilities#section-connection-state
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                @SuppressWarnings("ConstantConditions")
                boolean connected = snapshot.getValue(Boolean.class);

                if (!connected) {
                    if (plantList == null || plantList.size() == 0) {
                        if (view != null) {
                            view.showNoPlant(context.getString(R.string.no_plants));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void configurePlantListWidget(List<Plant> plantList) {
        // This widget code was inspired by https://github.com/amanjeetsingh150/Baking-App
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().putString(WIDGET, new Gson().toJson(plantList)).apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetId = new Bundle().getInt(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
        PlantWidget.updateAppWidget(context, appWidgetManager, appWidgetId, plantList);
    }
}

