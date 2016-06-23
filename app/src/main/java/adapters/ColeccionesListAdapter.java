package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giwahdavalos.gizo.R;
import java.util.Collections;
import java.util.List;

import rest.models.Coleccion;
import viewHolders.ColeccionesListViewHolder;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class ColeccionesListAdapter extends RecyclerView.Adapter<ColeccionesListViewHolder> {

    private final LayoutInflater inflater;
    private List<Coleccion> data = Collections.emptyList();

    public ColeccionesListAdapter(Context context, List<Coleccion> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ColeccionesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.coleccion_list_row, parent, false);
        ColeccionesListViewHolder holder = new ColeccionesListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ColeccionesListViewHolder holder, int position) {
        Coleccion coleccion = (Coleccion) this.data.get(position);

        holder.setTitle(coleccion.getTexto());
        holder.setSubtitle("0 Pictogramas");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
