package com.andrea.lettherebelife.features.details.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerPlantDetailsComponent;
import com.andrea.lettherebelife.databinding.ActivityPlantDetailsBinding;
import com.andrea.lettherebelife.features.details.PlantDetailsContract;
import com.andrea.lettherebelife.features.details.logic.PlantDetailsPresenter;
import com.andrea.lettherebelife.util.GlideUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class PlantDetailsActivity extends AppCompatActivity implements PlantDetailsContract.View {

    private ActivityPlantDetailsBinding binding;
    private boolean menuVisibility;
    private String menuTitle;

    @Inject
    PlantDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_details);

        ButterKnife.bind(this);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        DaggerPlantDetailsComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        presenter.connectView(this, getIntent().getExtras(), savedInstanceState);
    }

    @OnClick(R.id.share_fab)
    public void onShareSelected() {
        presenter.onShareSelected();
    }

    // region Plant Information menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plant_info_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_plant_information).setVisible(menuVisibility).setTitle(menuTitle);
        return true;
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
        binding.collapsingToolbarLayout.setTitle(title);
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
        binding.plantPhoto.setScaleType(CENTER_CROP);
        binding.plantPhoto.setImageBitmap(image);
    }

    @Override
    public void displayPlantImage(@NonNull String photoUrl) {
        GlideUtil.displayPlantImage(photoUrl, binding.plantPhoto);
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
    public void showMenu(boolean menuVisibility, @NonNull String menuTitle) {
        this.menuVisibility = menuVisibility;
        this.menuTitle = menuTitle;
        invalidateOptionsMenu();
    }

    @Override
    public void navigateToPlantInfo(@NonNull Intent intent) {
        startActivity(intent);
    }

    @Override
    public void sharePlantDetails(@NonNull String type, @NonNull String plantDetails) {
        Intent shareIntent = ShareCompat.IntentBuilder
                .from(this)
                .setType(type)
                .setText(plantDetails)
                .getIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(shareIntent);
    }

    @Override
    public void finishActivity() {
        finish();
    }
    // endregion
}
