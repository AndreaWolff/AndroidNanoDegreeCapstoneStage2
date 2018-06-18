package com.andrea.lettherebelife.features.common.repository;

import android.support.annotation.NonNull;

import com.andrea.lettherebelife.features.common.domain.PlantInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class PlantInfoRepositoryDefault implements PlantInfoRepository {

    private final PlantInfoDao plantInfoDao;

    @Inject
    PlantInfoRepositoryDefault(@NonNull PlantInfoDao plantInfoDao) {
        this.plantInfoDao = plantInfoDao;
    }

    @NonNull
    @Override
    public Single<List<PlantInfo>> getPlantInfo() {
        return plantInfoDao.getPlantInfo().flatMap(list -> Observable.fromIterable(list)
                .map(PlantInfoDto::toPlantInfo)
                .toList());
    }
}
