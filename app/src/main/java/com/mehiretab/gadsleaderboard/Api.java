package com.mehiretab.gadsleaderboard;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("/api/hours")
    Single<List<ApiLearningResponse>> getHoursResponse();

    @GET("/api/skilliq")
    Single<List<ApiSkillIqResponse>> getSkillIqResponse();

}
