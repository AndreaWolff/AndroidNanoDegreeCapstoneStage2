package com.andrea.lettherebelife.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.PlantInfo;
import com.google.gson.annotations.SerializedName;

class PlantInfoDto {

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
                name != null ? name : "N/A",
                description != null ? description : "N/A",
                optimalSun != null ? optimalSun : "N/A",
                plantingConsiderations != null ? plantingConsiderations : "N/A",
                growingFromSeed != null ? growingFromSeed : "N/A",
                otherCare != null ? otherCare : "N/A",
                diseases != null ? diseases : "N/A",
                harvesting != null ? harvesting : "N/A",
                storageUse != null ? storageUse : "N/A");
    }
}
