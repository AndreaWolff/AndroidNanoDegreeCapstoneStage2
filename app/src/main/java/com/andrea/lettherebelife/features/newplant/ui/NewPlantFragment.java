package com.andrea.lettherebelife.features.newplant.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.base.BaseFragment;
import com.andrea.lettherebelife.dagger.component.DaggerNewPlantComponent;
import com.andrea.lettherebelife.databinding.FragmentNewPlantBinding;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.newplant.NewPlantContract;
import com.andrea.lettherebelife.features.newplant.logic.NewPlantPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;
import static com.andrea.lettherebelife.features.common.ActivityConstants.IMAGE_REQUEST_CODE;

public class NewPlantFragment extends BaseFragment implements NewPlantContract.View {

    @Inject
    NewPlantPresenter presenter;

    private DatabaseReference messagesDatabaseReference;

    private FragmentNewPlantBinding binding;
    private boolean saveVisibility;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_plant, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_new_plant);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        messagesDatabaseReference = firebaseDatabase.getReference().child("plants");

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;

        activity.setSupportActionBar(binding.newPlantToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            setHasOptionsMenu(true);
        }

        binding.newPlantToolbar.setNavigationOnClickListener(v -> finishActivity());

        DaggerNewPlantComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);

        presenter.connectView(this, getActivity().getIntent().getExtras(), savedInstanceState);

        binding.newPlantCameraFab.setOnClickListener(v -> presenter.onAddImageSelected());
        setTextChangeListenerForFormEditTexts();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disconnectView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    // region Save menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_save).setEnabled(saveVisibility);
        super.onPrepareOptionsMenu(menu);
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

    private void setTextChangeListenerForFormEditTexts() {
        binding.newPlantNameEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onPlantNameTextChanged(charSequence);
            }
            @Override public void afterTextChanged(Editable editable) { }
        });

        binding.newPlantSeedDateEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onPlantSeedDateTextChanged(charSequence);
            }
            @Override public void afterTextChanged(Editable editable) { }
        });

        binding.newPlantDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onPlantDescriptionTextChanged(charSequence);
            }
            @Override public void afterTextChanged(Editable editable) { }
        });
    }

    // region View methods
    @Override
    public void setScreenTitle(@NonNull String title) {
        binding.newPlantCollapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void enableSaveButton(boolean saveVisibility) {
        this.saveVisibility = saveVisibility;
        invalidateMenuOptions();
    }

    @Override
    public void enterDataIntoDatabase(@NonNull Plant plant) {
        messagesDatabaseReference.push().setValue(plant);
        finishActivity();
    }

    @Override
    public void navigateToAddImage(@NonNull Intent intent, int image_id) {
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void renderPlantImage(@NonNull Bitmap imageBitmap) {
        binding.newPlantPhoto.setImageBitmap(imageBitmap);
    }

    @Override
    public void showPlantNameErrorMessage(@NonNull String plantNameErrorMessage) {
        binding.newPlantNameTextInputLayout.setError(plantNameErrorMessage);
    }

    @Override
    public void showSeedDateErrorMessage(@NonNull String seedDateErrorMessage) {
        binding.newPlantSeedDateTextInputLayout.setError(seedDateErrorMessage);
    }

    @Override
    public void showDescriptionErrorMessage(@NonNull String descriptionErrorMessage) {
        binding.newPlantDescriptionTextInputLayout.setError(descriptionErrorMessage);
    }

    @Override
    public void hidePlantNameErrorMessage() {
        binding.newPlantNameTextInputLayout.setErrorEnabled(false);
    }

    @Override
    public void hideSeedDateErrorMessage() {
        binding.newPlantSeedDateTextInputLayout.setErrorEnabled(false);
    }

    @Override
    public void hideDescriptionErrorMessage() {
        binding.newPlantDescriptionTextInputLayout.setErrorEnabled(false);
    }
    // endregion
}
