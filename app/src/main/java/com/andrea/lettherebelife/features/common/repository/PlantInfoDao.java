package com.andrea.lettherebelife.features.common.repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface PlantInfoDao {

    @GET("plants?api_key=9c8ed87dcedfa1f55f1a52409a6c8e3c") Single<List<PlantInfoDto>> getPlantInfo();

}
