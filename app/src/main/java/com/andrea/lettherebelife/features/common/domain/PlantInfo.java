package com.andrea.lettherebelife.features.common.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "plant_info")
public class PlantInfo {

    @PrimaryKey()
    private int id;
    private String name;
    private String description;
    private String optimalSun;
    private String plantingConsiderations;
    private String growingFromSeed;
    private String otherCare;
    private String diseases;
    private String harvesting;
    private String storageUse;

    public PlantInfo(int id,
                     @NonNull String name,
                     @NonNull String description,
                     @NonNull String optimalSun,
                     @NonNull String plantingConsiderations,
                     @NonNull String growingFromSeed,
                     @NonNull String otherCare,
                     @NonNull String diseases,
                     @NonNull String harvesting,
                     @NonNull String storageUse) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.optimalSun = optimalSun;
        this.plantingConsiderations = plantingConsiderations;
        this.growingFromSeed = growingFromSeed;
        this.otherCare = otherCare;
        this.diseases = diseases;
        this.harvesting = harvesting;
        this.storageUse = storageUse;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOptimalSun() {
        return optimalSun;
    }

    public String getPlantingConsiderations() {
        return plantingConsiderations;
    }

    public String getGrowingFromSeed() {
        return growingFromSeed;
    }

    public String getOtherCare() {
        return otherCare;
    }

    public String getDiseases() {
        return diseases;
    }

    public String getHarvesting() {
        return harvesting;
    }

    public String getStorageUse() {
        return storageUse;
    }
}
