package com.maintrineggmail.nytimesviewer.model.api;


import com.maintrineggmail.nytimesviewer.model.model.NYTimesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("{link}/all-sections/30")
    Call<NYTimesModel> getNYTimesModel(@Path("link") String link,@Query("api-key") String apiKey);
}
