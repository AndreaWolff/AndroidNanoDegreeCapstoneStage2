package com.andrea.lettherebelife.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.PlantInfo;
import com.google.gson.annotations.SerializedName;

import static com.andrea.lettherebelife.features.common.ActivityConstants.NOT_AVAILABLE;

public class PlantInfoDto {

    @SerializedName("id") private Integer id;
    @SerializedName("name") private String name;
    @SerializedName("description") private String description;
    @SerializedName("optimal_sun") private String optimalSun;
    @SerializedName("planting_considerations") private String plantingConsiderations;
    @SerializedName("growing_from_seed") private String growingFromSeed;
    @SerializedName("other_care") private String otherCare;
    @SerializedName("diseases") private String diseases;
    @SerializedName("harvesting") private String harvesting;
    @SerializedName("storage_use") private String storageUse;

    @NonNull
    PlantInfo toPlantInfo() {
        return new PlantInfo(id,
                name != null ? name : NOT_AVAILABLE,
                description != null ? description : NOT_AVAILABLE,
                optimalSun != null ? optimalSun : NOT_AVAILABLE,
                plantingConsiderations != null ? plantingConsiderations : NOT_AVAILABLE,
                growingFromSeed != null ? growingFromSeed : NOT_AVAILABLE,
                otherCare != null ? otherCare : NOT_AVAILABLE,
                diseases != null ? diseases : NOT_AVAILABLE,
                harvesting != null ? harvesting : NOT_AVAILABLE,
                storageUse != null ? storageUse : NOT_AVAILABLE);
    }
}
