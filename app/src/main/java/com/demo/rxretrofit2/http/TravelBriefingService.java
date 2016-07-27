package com.demo.rxretrofit2.http;


import com.demo.rxretrofit2.http.model.Country;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;


public interface TravelBriefingService {

    @GET("countries.json")
    Observable<List<Country>> getCountries();
}
