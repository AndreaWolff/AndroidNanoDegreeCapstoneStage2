package com.andrea.lettherebelife.features.main.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.base.BaseFragment;
import com.andrea.lettherebelife.dagger.component.DaggerMainComponent;
import com.andrea.lettherebelife.databinding.FragmentMainBinding;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.main.MainContract;
import com.andrea.lettherebelife.features.main.logic.MainPresenter;
import com.andrea.lettherebelife.jobintentservice.PlantJobIntentService;

import java.util.List;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class MainFragment extends BaseFragment implements MainContract.View, PlantAdapter.ListItemClickListener {

    @Inject
    MainPresenter presenter;

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_main);

        DaggerMainComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        binding.plantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.plantRecyclerView.setHasFixedSize(true);
        binding.plantRecyclerView.setNestedScrollingEnabled(true);

        presenter.connectView(this);

        binding.newPlantFab.setOnClickListener(v -> presenter.onNewPlantSelected());


        if (savedInstanceState == null) {
            PlantJobIntentService.enqueueWork(getActivity());
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
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
        binding.noPlantToDisplay.setVisibility(GONE);

        PlantAdapter adapter = new PlantAdapter(this, plantList);
        binding.plantRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showNoPlant(@NonNull String noPlant) {
        binding.noPlantToDisplay.setText(noPlant);
        binding.noPlantToDisplay.setVisibility(VISIBLE);
    }

    @Override
    public void navigateToNewPlantActivity(@NonNull Intent intent) {
        navigateToIntent(intent);
    }

    @Override
    public void navigateToPlantDetailsActivity(@NonNull Intent intent) {
        navigateToIntent(intent);
    }
    // endregion
}
