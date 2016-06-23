package rest.services;

import java.util.List;

import rest.models.Coleccion;
import rest.models.Usuario;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public interface AddColeccionService {

    @Headers({
            "User-Agent: Gizo-Retrofit"
    })
    @FormUrlEncoded
    @POST("/coleccion/add")
    Observable<Coleccion> addColeccion(
            @Field("idUsuario") String idUsuario,
            @Field("texto") String texto
    );
}
