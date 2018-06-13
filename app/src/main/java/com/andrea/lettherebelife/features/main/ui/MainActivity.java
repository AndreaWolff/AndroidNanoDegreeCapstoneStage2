package com.andrea.lettherebelife.features.main.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerMainComponent;
import com.andrea.lettherebelife.databinding.ActivityMainBinding;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.main.MainContract;
import com.andrea.lettherebelife.features.main.logic.MainPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class MainActivity extends AppCompatActivity implements MainContract.View, PlantAdapter.ListItemClickListener {

    ActivityMainBinding binding;

    @Inject MainPresenter presenter;

    private ChildEventListener childEventListener;
    private ValueEventListener valueEventListener;
    private DatabaseReference messagesDatabaseReference;
    private List<Plant> plantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("plants");

        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        binding.plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.plantRecyclerView.setHasFixedSize(true);
        binding.plantRecyclerView.setNestedScrollingEnabled(true);

        presenter.connectView(this);

        attachDatabaseReadListener();
    }

    @OnClick(R.id.new_plant_fab)
    public void onNewPlantSelected() {
        presenter.onNewPlantSelected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachDatabaseReadListener(childEventListener, valueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    @Override
    public void onListItemClicked() {
        presenter.onPlantListItemSelected();
    }

    // region View methods
    @Override
    public void showPlantList(@NonNull List<Plant> plantList) {
        PlantAdapter adapter = new PlantAdapter(this, plantList);
        binding.plantRecyclerView.setAdapter(adapter);
    }

    @Override
    public void navigateToNewPlantActivity(@NonNull Intent intent) {
        startActivity(intent);
    }

    @Override
    public void navigateToPlantDetailsActivity(@NonNull Intent intent) {
        startActivity(intent);
    }

    @Override
    public void detachChildEventListener() {
        messagesDatabaseReference.removeEventListener(childEventListener);
        childEventListener = null;
    }

    @Override
    public void detachValueEventListener() {
        messagesDatabaseReference.removeEventListener(valueEventListener);
        valueEventListener = null;
    }
    // endregion

    //TODO: Look at this!!! https://www.learnhowtoprogram.com/android/gestures-animations-flexible-uis/using-the-camera-and-saving-images-to-firebase
    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                    Plant plant = dataSnapshot.getValue(Plant.class);
                    plantList.add(plant);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };

            if (valueEventListener == null) {
                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        presenter.setPlantList(plantList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
            }

            messagesDatabaseReference.addChildEventListener(childEventListener);
            messagesDatabaseReference.addValueEventListener(valueEventListener);
        }
    }
}
