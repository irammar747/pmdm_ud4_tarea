package dam.pmdm.spyrothedragon.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.canvas.FlameView;
import dam.pmdm.spyrothedragon.models.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> list;

    public CharactersAdapter(List<Character> charactersList) {
        this.list = charactersList;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);


        holder.imageImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(character.getImage().equals("spyro")){
                    mostrarAnimacionLlama(holder);
                }
                return true;
            }
        });
    }

    private void mostrarAnimacionLlama(CharactersViewHolder holder) {
        Context context = holder.itemView.getContext();
        ViewGroup rootView = (ViewGroup) holder.itemView.getRootView(); // Obtener la vista raíz

        // Crear instancia de FlameView
        FlameView flameView = new FlameView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200, 200);

        // Obtener posición absoluta de imageImageView
        int[] location = new int[2];
        holder.imageImageView.getLocationOnScreen(location);

        params.leftMargin = location[0] + holder.imageImageView.getWidth() / 2 - 100;
        params.topMargin = location[1] + holder.imageImageView.getHeight() / 2 - 100;
        flameView.setLayoutParams(params);

        // Asegurar que rootView es un FrameLayout
        if (rootView instanceof FrameLayout) {
            ((FrameLayout) rootView).addView(flameView);
        } else {
            Toast.makeText(context, "Error: No se puede mostrar la animación", Toast.LENGTH_SHORT).show();
            return;
        }

        // Quitar la animación después de 2 segundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ((FrameLayout) rootView).removeView(flameView);
        }, 2000);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
}
