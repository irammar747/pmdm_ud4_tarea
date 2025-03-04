package dam.pmdm.spyrothedragon.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.adapters.CollectiblesAdapter;
import dam.pmdm.spyrothedragon.databinding.FragmentCollectiblesBinding;
import dam.pmdm.spyrothedragon.models.Collectible;

public class CollectiblesFragment extends Fragment {

    private FragmentCollectiblesBinding binding;
    private RecyclerView recyclerView;
    private CollectiblesAdapter adapter;
    private List<Collectible> collectiblesList;
    private VideoView videoView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCollectiblesBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerViewCollectibles;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        collectiblesList = new ArrayList<>();
        adapter = new CollectiblesAdapter(collectiblesList, this);
        recyclerView.setAdapter(adapter);

        loadCollectibles();

        videoView = binding.videoView;
        videoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video_spyro);
        // Asegúrate de que el VideoView esté oculto al principio
        videoView.setVisibility(View.GONE);

        return binding.getRoot();
    }

    public void activateEasterEgg() {
        // Mostrar el VideoView en pantalla completa
        binding.videoView.setVisibility(View.VISIBLE); // Mostrar contenedor del video

        videoView.start();

        // Escuchar cuando el video termine
        videoView.setOnCompletionListener(mp -> {
            // Ocultar el VideoView
            videoView.setVisibility(View.GONE);

            // Aquí podrías hacer alguna acción adicional si es necesario
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadCollectibles() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.collectibles);

            // Crear un parser XML
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            Collectible currentCollectible = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if ("collectible".equals(tagName)) {
                            currentCollectible = new Collectible();
                        } else if (currentCollectible != null) {
                            if ("name".equals(tagName)) {
                                currentCollectible.setName(parser.nextText());
                            } else if ("description".equals(tagName)) {
                                currentCollectible.setDescription(parser.nextText());
                            } else if ("image".equals(tagName)) {
                                currentCollectible.setImage(parser.nextText());
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();

                        if ("collectible".equals(tagName) && currentCollectible != null) {
                            collectiblesList.add(currentCollectible);
                        }
                        break;
                }

                eventType = parser.next();
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
