package com.andrea.lettherebelife.features.plantinfo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerPlantInfoComponent;
import com.andrea.lettherebelife.databinding.ActivityPlantInfoBinding;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class PlantInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlantInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_info);

        DaggerPlantInfoComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        PlantInfoViewModelFactory factory = new PlantInfoViewModelFactory();
        PlantInfoViewModel viewModel = ViewModelProviders.of(this, factory).get(PlantInfoViewModel.class);
        viewModel.setPlantInfoViewModel(getIntent().getExtras());

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String plantName = extras.getString("PLANT NAME");
            if (plantName != null && !plantName.isEmpty()) {
                setTitle("About " + plantName);
            } else {
                setTitle(this.getString(R.string.app_name));
            }
        }
    }
}
