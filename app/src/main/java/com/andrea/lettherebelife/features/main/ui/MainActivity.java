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

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class MainActivity extends AppCompatActivity implements MainContract.View, PlantAdapter.ListItemClickListener {

    @Inject MainPresenter presenter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        binding.plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.plantRecyclerView.setHasFixedSize(true);
        binding.plantRecyclerView.setNestedScrollingEnabled(true);

        presenter.connectView(this);
    }

    @OnClick(R.id.new_plant_fab)
    public void onNewPlantSelected() {
        presenter.onNewPlantSelected();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    @Override
    public void onListItemClicked(@NonNull Plant plant) {
        presenter.onPlantListItemSelected(plant);
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
    // endregion
}
