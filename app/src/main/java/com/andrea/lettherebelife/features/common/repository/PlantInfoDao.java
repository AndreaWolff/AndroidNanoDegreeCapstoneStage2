package com.andrea.lettherebelife.features.common.repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

import static com.andrea.lettherebelife.features.common.ActivityConstants.API_KEY;

public interface PlantInfoDao {

    @GET("plants?api_key=" + API_KEY) Single<List<PlantInfoDto>> getPlantInfo();

}
