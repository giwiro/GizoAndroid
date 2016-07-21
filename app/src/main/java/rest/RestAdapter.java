package rest;

import android.provider.SyncStateContract;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class RestAdapter {

    private static Retrofit sharedInstance = null;

    public static Retrofit getInstance() {
        if (sharedInstance == null){

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            sharedInstance = new Retrofit.Builder()
                    .baseUrl("https://gizo.herokuapp.com")
                    //.baseUrl("http://192.168.0.101")
                    //.baseUrl("http://192.168.1.35")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return sharedInstance;
    }
}
