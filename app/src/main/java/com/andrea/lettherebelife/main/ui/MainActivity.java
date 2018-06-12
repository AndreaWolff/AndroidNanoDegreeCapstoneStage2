package com.andrea.lettherebelife.main.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.databinding.ActivityMainBinding;
import com.andrea.lettherebelife.details.ui.PlantDetailsActivity;
import com.andrea.lettherebelife.main.MainContract;
import com.andrea.lettherebelife.newplant.ui.NewPlantActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View, PlantAdapter.ListItemClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ButterKnife.bind(this);

        binding.plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.plantRecyclerView.setHasFixedSize(true);
        binding.plantRecyclerView.setNestedScrollingEnabled(true);

        PlantAdapter adapter = new PlantAdapter(this);
        binding.plantRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.new_plant_fab)
    public void onNewPlantSelected() {
        Intent intent = new Intent(this, NewPlantActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListItemClicked() {
        Intent intent = new Intent(this, PlantDetailsActivity.class);
        startActivity(intent);
    }
}
