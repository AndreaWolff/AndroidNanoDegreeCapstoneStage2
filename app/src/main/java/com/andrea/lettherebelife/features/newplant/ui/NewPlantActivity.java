package com.andrea.lettherebelife.features.newplant.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerNewPlantComponent;
import com.andrea.lettherebelife.databinding.ActivityNewPlantBinding;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.newplant.NewPlantContract;
import com.andrea.lettherebelife.features.newplant.logic.NewPlantPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class NewPlantActivity extends AppCompatActivity implements NewPlantContract.View {

    @Inject NewPlantPresenter presenter;

    private DatabaseReference messagesDatabaseReference;

    private ActivityNewPlantBinding binding;
    private boolean saveVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_plant);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("plants");

        ButterKnife.bind(this);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        DaggerNewPlantComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        presenter.connectView(this, getIntent().getExtras(), savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    @OnTextChanged(R.id.new_plant_name_editText)
    public void onPlantNameTextChanged(@NonNull CharSequence charSequence) {
        presenter.onPlantNameTextChanged(charSequence);
    }

    @OnTextChanged(R.id.new_plant_seed_date_editText)
    public void onPlantSeedDateTextChanged(@NonNull CharSequence charSequence) {
        presenter.onPlantSeedDateTextChanged(charSequence);
    }

    @OnTextChanged(R.id.new_plant_description_editText)
    public void onPlantDescriptionTextChanged(@NonNull CharSequence charSequence) {
        presenter.onPlantDescriptionTextChanged(charSequence);
    }

    @OnClick(R.id.new_plant_camera_fab)
    public void onAddImageSelected() {
        presenter.onAddImageSelected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    // region Save menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_save).setEnabled(saveVisibility);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                presenter.onSaveSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // endregion

    // region View methods
    @Override
    public void setScreenTitle(@NonNull String title) {
        binding.collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void enableSaveButton(boolean saveVisibility) {
        this.saveVisibility = saveVisibility;
        invalidateOptionsMenu();
    }

    @Override
    public void enterDataIntoDatabase(@NonNull Plant plant) {
        messagesDatabaseReference.push().setValue(plant);
        finish();
    }

    @Override
    public void navigateToAddImage(@NonNull Intent intent, int image_id) {
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, 111);
        }
    }

    @Override
    public void renderPlantImage(@NonNull Bitmap imageBitmap) {
        binding.plantPhoto.setImageBitmap(imageBitmap);
    }
    // endregion
}
