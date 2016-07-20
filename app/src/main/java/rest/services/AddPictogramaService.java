package rest.services;

import okhttp3.MultipartBody;
import rest.models.Pictograma;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Gi Wah Davalos on 26/06/2016.
 */
public interface AddPictogramaService {
    @Headers({
            "User-Agent: Gizo-Retrofit"
    })
    @Multipart
    @POST("/pictograma/add")
    Observable<Pictograma> addPictograma(
            @Query("nombre") String nombre,
            @Query("idColeccion") String idColeccion,
            @Part MultipartBody.Part foto,
            @Part MultipartBody.Part audio
    );
}
