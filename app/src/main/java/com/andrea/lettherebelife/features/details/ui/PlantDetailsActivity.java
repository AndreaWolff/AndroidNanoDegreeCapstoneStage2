package com.andrea.lettherebelife.features.details.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andrea.lettherebelife.R;

public class PlantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainPlanDetailsFragment, new PlantDetailsFragment())
                    .commit();
        }
    }
}
