package ui.recyclerView.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giwahdavalos.gizo.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.List;

import rest.models.Coleccion;
import rest.models.Pictograma;
import ui.activities.DetallePictograma;
import ui.recyclerView.viewHolders.ColeccionesListViewHolder;
import ui.recyclerView.viewHolders.PictogramasListViewHolder;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class PictogramasListAdapter extends RecyclerView.Adapter<PictogramasListViewHolder>  {

    private final LayoutInflater inflater;
    private List<Pictograma> data = Collections.emptyList();
    private final Context ctx;
    private final Coleccion coleccion;

    public PictogramasListAdapter(Context context, Coleccion coleccion) {
        inflater = LayoutInflater.from(context);
        this.ctx = context;
        this.data = coleccion.getPictogramas();
        this.coleccion = coleccion;
    }

    @Override
    public PictogramasListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pictogramas_list_row, parent, false);
        PictogramasListViewHolder holder = new PictogramasListViewHolder(view, this.data);
        return holder;
    }

    @Override
    public void onBindViewHolder(PictogramasListViewHolder holder, int position) {
        Pictograma pictograma = (Pictograma) this.data.get(position);
        File file = new File(Environment
                .getExternalStoragePublicDirectory("/" +
                                this.ctx.getResources().getString(R.string.app_name) + "/" +
                                this.coleccion.get_id()
                ),
                pictograma.getFileName());
        Log.d("BIND_VIEW_HOLDER: " + position, file.exists() +"");
        if (file.exists()) {
            Log.d("FILE_EXISTS", file.exists() + "");
            Picasso
                    .with(ctx)
                    .load(file)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .fit().centerInside()
                    .into(holder.image_view);
            holder.titulo_pictograma.setText(pictograma.getNombre());

        }

        holder.itemView.setOnClickListener( v -> {
            Intent i = new Intent(this.ctx, DetallePictograma.class);
            ActivityOptions options
                    = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) this.ctx,
                    Pair.create(holder.image_view, holder.image_view.getTransitionName()),
                    Pair.create(holder.titulo_pictograma, holder.titulo_pictograma.getTransitionName()));
            i.putExtra("pictograma", pictograma);
            i.putExtra("coleccion", this.coleccion);
            this.ctx.startActivity(i, options.toBundle());
        });

        //holder.setImage(file);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void push(Pictograma pictograma) {
        this.data.add(pictograma);
        notifyDataSetChanged();
    }
}
