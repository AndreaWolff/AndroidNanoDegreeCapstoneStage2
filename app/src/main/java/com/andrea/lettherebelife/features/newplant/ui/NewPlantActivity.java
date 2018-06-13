package com.andrea.lettherebelife.features.newplant.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerNewPlantComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class NewPlantActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        DaggerNewPlantComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);
    }
}
