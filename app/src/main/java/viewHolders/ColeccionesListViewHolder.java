package viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.giwahdavalos.gizo.R;

/**
 * Created by Gi Wah Davalos on 22/06/2016.
 */
public class ColeccionesListViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView subtitle;

    public ColeccionesListViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);

    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.setText(subtitle);
    }
}
