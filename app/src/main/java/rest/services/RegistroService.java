package rest.services;

import rest.models.Usuario;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Gi Wah Davalos on 16/05/2016.
 */
public interface RegistroService {

    @Headers({
            "User-Agent: Gizo-Retrofit"
    })
    @FormUrlEncoded
    @POST("/registro")
    Observable<Usuario> registro(
            @Field("nombres") String nombres,
            @Field("apellidos") String apellidos,
            @Field("email") String email,
            @Field("password") String password
    );
}
