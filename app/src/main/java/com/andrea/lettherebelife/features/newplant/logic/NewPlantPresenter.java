package com.andrea.lettherebelife.features.newplant.logic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.newplant.NewPlantContract;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static com.andrea.lettherebelife.features.common.ActivityConstants.IMAGE_ID;
import static com.andrea.lettherebelife.features.common.ActivityConstants.IMAGE_REQUEST_CODE;
import static com.andrea.lettherebelife.features.common.ActivityConstants.PLANT_PHOTO;
import static com.andrea.lettherebelife.util.DecodeImageFromFirebase.decodeImageFromFirebaseBase64;
import static com.andrea.lettherebelife.util.EncodeImageForFirebase.encodeBitmapAndSaveToFirebase;

public class NewPlantPresenter {

    private final Context context;

    private NewPlantContract.View view;
    private CharSequence plantName;
    private CharSequence plantSeedDate;
    private CharSequence plantDescription;
    private boolean isValidPlantName;
    private boolean isValidPlantSeedDate;
    private boolean isValidPlantDescription;
    private String photoUrl;

    @Inject
    NewPlantPresenter(@NonNull Context context) {
        this.context = context;
    }

    public void connectView(@Nullable NewPlantContract.View view, @Nullable Bundle extras, @Nullable Bundle savedInstanceState) {
        this.view = view;

        init();

        if (savedInstanceState != null) {
            String plant_photo = savedInstanceState.getString(PLANT_PHOTO);
            if (plant_photo != null) {
                displayPlantPhoto(decodeImageFromFirebaseBase64(plant_photo));
            }
        }
    }

    private void init() {
        if (view != null) {
            view.setScreenTitle(context.getString(R.string.new_plant_title));
        }
    }

    public void onSavedInstanceState(Bundle outState) {
        outState.putString(PLANT_PHOTO, photoUrl);
    }

    public void onAddImageSelected() {
        if (view != null) {
            Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
            view.navigateToAddImage(intent, IMAGE_ID);
        }
    }

    public void onSaveSelected() {
        if (view != null) {
            String photo = photoUrl != null ? photoUrl : "";
            view.enterDataIntoDatabase(new Plant(plantName.toString(), plantSeedDate.toString(), plantDescription.toString(), photo));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                displayPlantPhoto(imageBitmap);
            }
        }
    }

    private void displayPlantPhoto(Bitmap imageBitmap) {
        if (imageBitmap != null) {
            if (view != null) {
                view.renderPlantImage(imageBitmap);
            }

            photoUrl = encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void onPlantNameTextChanged(@NonNull CharSequence plantName) {
        isValidPlantName = checkInput(plantName);

        if (isValidPlantName) {
            this.plantName = plantName;
            if (view != null) {
                view.hidePlantNameErrorMessage();
            }
        } else {
            if (view != null) {
                view.showPlantNameErrorMessage(context.getString(R.string.min_characters_required));
            }
        }

        validateForm();
    }

    public void onPlantSeedDateTextChanged(@NonNull CharSequence plantSeedDate) {
        isValidPlantSeedDate = checkInput(plantSeedDate);

        if (isValidPlantSeedDate) {
            this.plantSeedDate = plantSeedDate;
            if (view != null) {
                view.hideSeedDateErrorMessage();
            }
        } else {
            if (view != null) {
                view.showSeedDateErrorMessage(context.getString(R.string.min_characters_required));
            }
        }

        validateForm();
    }

    public void onPlantDescriptionTextChanged(@NonNull CharSequence plantDescription) {
        isValidPlantDescription = checkInput(plantDescription);

        if (isValidPlantDescription) {
            this.plantDescription = plantDescription;
            if (view != null) {
                view.hideDescriptionErrorMessage();
            }
        } else {
            if (view != null) {
                view.showDescriptionErrorMessage(context.getString(R.string.min_characters_required));
            }
        }

        validateForm();
    }

    private void validateForm() {
        if (isValidPlantName && isValidPlantSeedDate && isValidPlantDescription) {
            if (view != null) {
                view.enableSaveButton(true);
                return;
            }
        }

        if (view != null) {
            view.enableSaveButton(false);
        }
    }

    private boolean checkInput(@NonNull CharSequence charSequence) {
        return charSequence.toString().length() >= 3;
    }

    public void disconnectView() {
        view = null;
    }
}
