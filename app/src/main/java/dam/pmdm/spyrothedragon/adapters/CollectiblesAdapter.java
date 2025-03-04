package dam.pmdm.spyrothedragon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Collectible;
import dam.pmdm.spyrothedragon.ui.CollectiblesFragment;


public class CollectiblesAdapter extends RecyclerView.Adapter<CollectiblesAdapter.CollectiblesViewHolder> {

    private List<Collectible> list;
    private int toques = 0;
    private CollectiblesFragment fragment;

    public CollectiblesAdapter(List<Collectible> collectibleList, CollectiblesFragment fragment) {

        this.list = collectibleList;
        this.fragment = fragment;
    }

    @Override
    public CollectiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        return new CollectiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectiblesViewHolder holder, int position) {
        Collectible collectible = list.get(position);
        holder.nameTextView.setText(collectible.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(collectible.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);

        holder.imageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collectible.getImage().equals("gems")){
                    toques++;
                    if(toques == 4){
                        toques = 0;
                        fragment.activateEasterEgg();
                    }else{
                        //Reseteamos el contador si ha pasado más de 1 seg desde el último toque y no ha llegado a 4
                        v.postDelayed(() -> toques = 0, 1000);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CollectiblesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CollectiblesViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
