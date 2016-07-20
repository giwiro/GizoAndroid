package ui.recyclerView.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

import java.util.List;

import rest.models.Coleccion;
import ui.activities.DetalleColeccion;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class ColeccionesListViewHolder extends RecyclerView.ViewHolder {

    private TextView subtitle;
    private TextView title;
    private final Context ctx;
    private List<Coleccion> colecciones;

    public ColeccionesListViewHolder(View itemView, List<Coleccion> data) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.title);
        this.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        this.ctx = itemView.getContext();
        this.colecciones = data;

        itemView.setOnClickListener( v -> {
            final Intent intent;
            final int position = getAdapterPosition();
            final Coleccion coleccion = this.colecciones.get(position);

            Log.e("BINDING_TO: " + position, "#Pic: " + coleccion.getPictogramas().size());

            //Log.d("DETALLE_COLECCION class", DetalleColeccion.class + "");

            intent = new Intent(this.ctx, DetalleColeccion.class);
            intent.putExtra("coleccion", coleccion);
            this.ctx.startActivity(intent);
        });

    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }
}
