package com.andrea.lettherebelife.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.PlantInfo;

import java.util.List;

import io.reactivex.Single;

public interface PlantInfoRepository {

    @NonNull Single<List<PlantInfo>> getPlantInfo();

}
