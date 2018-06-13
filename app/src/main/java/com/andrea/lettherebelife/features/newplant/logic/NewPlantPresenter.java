package com.andrea.lettherebelife.features.newplant.logic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.newplant.NewPlantContract;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

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
    }

    private void init() {
        if (view != null) {
            view.setScreenTitle("Create a New Plant");
        }
    }

    public void onAddImageSelected() {
        if (view != null) {
            Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
            // TODO: change that to a common variable
            view.navigateToAddImage(intent, 2);
        }
    }

    public void onSaveSelected() {
        if (view != null) {
            String photo = photoUrl != null ? photoUrl : "";
            view.enterDataIntoDatabase(new Plant(plantName.toString(), plantSeedDate.toString(), plantDescription.toString(), photo));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                if (imageBitmap != null) {
                    if (view != null) {
                        view.renderPlantImage(imageBitmap);
                    }

                    encodeBitmapAndSaveToFirebase(imageBitmap);
                }
            }
        }
    }

    // Issues storing photos taken from https://code.tutsplus.com/tutorials/image-upload-to-firebase-in-android-application--cms-29934
    private void encodeBitmapAndSaveToFirebase(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        photoUrl = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    // TODO: check to see if char sequences are over so many characters and then do an error if not
    public void onPlantNameTextChanged(@NonNull CharSequence plantName) {
        isValidPlantName = checkInput(plantName);

        if (isValidPlantName) {
            this.plantName = plantName;
        }

        validateForm();
    }

    public void onPlantSeedDateTextChanged(@NonNull CharSequence plantSeedDate) {
        isValidPlantSeedDate = checkInput(plantSeedDate);

        if (isValidPlantSeedDate) {
            this.plantSeedDate = plantSeedDate;
        }

        validateForm();
    }

    public void onPlantDescriptionTextChanged(@NonNull CharSequence plantDescription) {
        isValidPlantDescription = checkInput(plantDescription);

        if (isValidPlantDescription) {
            this.plantDescription = plantDescription;
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
        return charSequence.toString().trim().length() > 0;
    }

    public void disconnectView() {
        view = null;
    }
}
