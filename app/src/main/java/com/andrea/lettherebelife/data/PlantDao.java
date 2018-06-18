package com.andrea.lettherebelife.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.andrea.lettherebelife.features.common.domain.PlantInfo;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PlantDao {

    @Query("SELECT * FROM plant_info")
    Single<List<PlantInfo>> loadAllPlantInfo();

    @Query("SELECT * FROM plant_info WHERE id = :id")
    Single<PlantInfo> loadPlantInfoById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsertPlantInfo(List<PlantInfo> plantInfo);

}
