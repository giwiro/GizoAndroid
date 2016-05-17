package rest;

import android.provider.SyncStateContract;
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

            sharedInstance = new Retrofit.Builder()
                    .baseUrl("https://gizo.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return sharedInstance;
    }
}
