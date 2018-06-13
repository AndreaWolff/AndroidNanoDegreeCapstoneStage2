package com.andrea.lettherebelife.features.plantinfo.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerPlantInfoComponent;
import com.andrea.lettherebelife.databinding.ActivityPlantInformationBinding;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class PlantInfoActivity extends AppCompatActivity {

    ActivityPlantInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_information);

        DaggerPlantInfoComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);
    }
}
