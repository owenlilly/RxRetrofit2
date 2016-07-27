package com.demo.rxretrofit2.http;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


public class RxHttpServiceFactory {

    private static Retrofit retrofit;

    public static <T> T create(Class<T> cls){
        return getRetrofit().create(cls);
    }

    public static TravelBriefingService travelBriefing(){
        return create(TravelBriefingService.class);
    }

    public static Retrofit getRetrofit(){
        if(retrofit == null) {
            synchronized (RxHttpServiceFactory.class) {
                if(retrofit == null) {

                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl("https://travelbriefing.org/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                            .build();
                }
            }
        }

        return retrofit;
    }
}
