package ui.recyclerView.viewHolders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import rest.models.Coleccion;
import rest.models.Pictograma;
import ui.activities.DetalleColeccion;
import ui.activities.DetallePictograma;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class PictogramasListViewHolder extends RecyclerView.ViewHolder {

    //private TextView subtitle;
    public ImageView image_view;
    public TextView titulo_pictograma;
    private final Context ctx;
    List<Pictograma> pictogramas;
    public View itemView;

    public PictogramasListViewHolder(View itemView, List<Pictograma> data) {
        super(itemView);
        this.itemView = itemView;
        this.image_view = (ImageView) itemView.findViewById(R.id.card_image);
        this.titulo_pictograma = (TextView) itemView.findViewById(R.id.titulo_pictograma);
        //this.title = (TextView) itemView.findViewById(R.id.title);
        this.ctx = itemView.getContext();
        this.pictogramas = data;



    }

}
