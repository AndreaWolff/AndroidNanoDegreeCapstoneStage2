package com.andrea.lettherebelife.features.details.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.base.BaseFragment;
import com.andrea.lettherebelife.dagger.component.DaggerPlantDetailsComponent;
import com.andrea.lettherebelife.databinding.FragmentPlantDetailsBinding;
import com.andrea.lettherebelife.features.details.PlantDetailsContract;
import com.andrea.lettherebelife.features.details.logic.PlantDetailsPresenter;
import com.andrea.lettherebelife.util.GlideUtil;

import javax.inject.Inject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class PlantDetailsFragment extends BaseFragment implements PlantDetailsContract.View {

    private FragmentPlantDetailsBinding binding;
    private boolean menuVisibility;
    private String menuTitle;

    @Inject
    PlantDetailsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_details, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_plant_details);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;

        activity.setSupportActionBar(binding.plantDetailsToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            setHasOptionsMenu(true);
        }

        binding.plantDetailsToolbar.setNavigationOnClickListener(v -> finishCurrentActivity());

        DaggerPlantDetailsComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        presenter.connectView(this, getActivity().getIntent().getExtras());

        binding.shareFab.setOnClickListener(v -> presenter.onShareSelected());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    // region Plant Information menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.plant_info_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_plant_information).setVisible(menuVisibility).setTitle(menuTitle);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_plant_information:
                presenter.showPlantInfo();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion

    // region View methods
    @Override
    public void setScreenTitle(@NonNull String title) {
        binding.plantDetailsCollapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void renderSeedDate(@NonNull String seedDate) {
        binding.plantDetailsSeedDateTextView.setText(seedDate);
    }

    @Override
    public void renderDescription(@NonNull String description) {
        binding.plantDetailsDescriptionTextView.setText(description);
    }

    @Override
    public void displayPlantImageBitmap(@NonNull Bitmap image) {
        binding.plantDetailsPhoto.setScaleType(CENTER_CROP);
        binding.plantDetailsPhoto.setImageBitmap(image);
    }

    @Override
    public void displayPlantImage(@NonNull String photoUrl) {
        GlideUtil.displayPlantImage(photoUrl, binding.plantDetailsPhoto);
    }

    @Override
    public void showProgressBar() {
        binding.plantDetailsProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.plantDetailsProgressBar.setVisibility(GONE);
    }

    @Override
    public void showErrorDialog(@NonNull String errorTitle, @NonNull String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    // do nothing
                });
        builder.create();
        builder.show();
    }

    @Override
    public void showMenu(boolean menuVisibility, @NonNull String menuTitle) {
        this.menuVisibility = menuVisibility;
        this.menuTitle = menuTitle;
        invalidateMenuOptions();
    }

    @Override
    public void navigateToPlantInfo(@NonNull Intent intent) {
        startActivity(intent);
    }

    @Override
    public void sharePlantDetails(@NonNull String type, @NonNull String plantDetails) {
        Intent shareIntent = ShareCompat.IntentBuilder
                .from(getActivity())
                .setType(type)
                .setText(plantDetails)
                .getIntent()
                .addFlags(FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(shareIntent);
    }

    @Override
    public void finishCurrentActivity() {
        finishActivity();
    }
    // endregion
}
