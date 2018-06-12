package com.andrea.lettherebelife.plantinformation.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.databinding.ActivityPlantInformationBinding;

public class PlantInformationActivity extends AppCompatActivity {

    ActivityPlantInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_information);
    }
}
