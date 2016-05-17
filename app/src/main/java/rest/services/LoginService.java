package rest.services;

import rest.models.Usuario;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public interface LoginService {

    @Headers({
            "User-Agent: Gizo-Retrofit"
    })
    @FormUrlEncoded
    @POST("/login")
    Observable<Usuario> login(
            @Field("email") String email, @Field("password") String password
    );
}
