package dam.pmdm.spyrothedragon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import dam.pmdm.spyrothedragon.canvas.ArrowView;
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.CharactersGuideBinding;
import dam.pmdm.spyrothedragon.databinding.CollectionsGuideBinding;
import dam.pmdm.spyrothedragon.databinding.InfoGuideBinding;
import dam.pmdm.spyrothedragon.databinding.ResumeGuideBinding;
import dam.pmdm.spyrothedragon.databinding.WelcomeGuideBinding;
import dam.pmdm.spyrothedragon.databinding.WorldsGuideBinding;
import dam.pmdm.spyrothedragon.preferences.Preferences;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private WelcomeGuideBinding welcomeGuideBinding;
    private CharactersGuideBinding charactersGuideBinding;
    private WorldsGuideBinding worldsGuideBinding;
    private CollectionsGuideBinding collectionsGuideBinding;
    private InfoGuideBinding infoGuideBinding;
    private ResumeGuideBinding resumeGuideBinding;
    NavController navController = null;

    private int currentStep = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar Toolbar como ActionBar
        setSupportActionBar(binding.toolbar);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
            // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(destination.getId() != R.id.navigation_characters &&
                    destination.getId() != R.id.navigation_worlds &&
                    destination.getId() != R.id.navigation_collectibles);
        });

        //Preferences.setGuideCompleted(this, false);
        if(!Preferences.isGuideCompleted(this)) {
            showGuide();
        }

    }

    private void showGuide() {
        binding.guideContainer.setVisibility(View.VISIBLE);
        showStep(currentStep);

    }

    private void showStep(int currentStep) {
        View marcador;

        binding.guideContainer.removeAllViews();

        switch (currentStep) {
            case 0:
                welcomeGuideBinding = WelcomeGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);
                welcomeGuideBinding.btnComenzarImg.setOnClickListener(this::nextStep);

                animarTextoComenzar();

                break;
            case 1:
                binding.navView.setSelectedItemId(R.id.nav_characters);
                charactersGuideBinding = CharactersGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);

                marcador = charactersGuideBinding.marcador;
                charactersGuideBinding.btnSkip.setOnClickListener(this::skipGuide);

                AnimatorSet animatorSetCharacters = moverMarcadorMenu(marcador, 0);
                animatorSetCharacters.start();

                animatorSetCharacters.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        pintarFlecha(charactersGuideBinding.arrowView, marcador, charactersGuideBinding.bocadillo);
                    }
                });

                marcador.setOnClickListener(v->{
                    charactersGuideBinding.arrowView.setVisibility(View.GONE);
                    marcador.setVisibility(View.GONE);
                    binding.navView.setSelectedItemId(R.id.nav_characters);
                    charactersGuideBinding.btnNext.setVisibility(View.VISIBLE);
                    charactersGuideBinding.bocadillo.setText(R.string.bocadillo_personajes2);
                    charactersGuideBinding.btnNext.setOnClickListener(this::nextStep);

                });

                break;
            case 2:

                worldsGuideBinding = WorldsGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);

                marcador = worldsGuideBinding.marcador;
                worldsGuideBinding.btnSkip.setOnClickListener(this::skipGuide);

                AnimatorSet animatorSetWorlds = moverMarcadorMenu(marcador, 1);
                animatorSetWorlds.start();

                animatorSetWorlds.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        pintarFlecha(worldsGuideBinding.arrowView, marcador, worldsGuideBinding.bocadillo);
                    }
                });

                marcador.setOnClickListener(v->{
                    worldsGuideBinding.arrowView.setVisibility(View.GONE);
                    marcador.setVisibility(View.GONE);
                    binding.navView.setSelectedItemId(R.id.nav_worlds);


                    worldsGuideBinding.btnNext.setVisibility(View.VISIBLE);
                    worldsGuideBinding.bocadillo.setText(R.string.bocadillo_mundos);
                    worldsGuideBinding.btnNext.setOnClickListener(MainActivity.this::nextStep);

                });
                break;
            case 3:
                collectionsGuideBinding = CollectionsGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);

                marcador = collectionsGuideBinding.marcador;
                collectionsGuideBinding.btnSkip.setOnClickListener(this::skipGuide);

                AnimatorSet animatorSetCollections = moverMarcadorMenu(marcador, 2);
                animatorSetCollections.start();

                animatorSetCollections.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        pintarFlecha(collectionsGuideBinding.arrowView, marcador, collectionsGuideBinding.bocadillo);
                    }
                });

                marcador.setOnClickListener(v->{
                    collectionsGuideBinding.arrowView.setVisibility(View.GONE);
                    marcador.setVisibility(View.GONE);
                    binding.navView.setSelectedItemId(R.id.nav_collectibles);
                    collectionsGuideBinding.btnNext.setVisibility(View.VISIBLE);
                    collectionsGuideBinding.bocadillo.setText(R.string.bocadillo_colecciones2);
                    collectionsGuideBinding.btnNext.setOnClickListener(this::nextStep);

                });
                break;
            case 4:
                infoGuideBinding = InfoGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);

                marcador = infoGuideBinding.marcador;
                infoGuideBinding.btnSkip.setOnClickListener(this::skipGuide);

                AnimatorSet animatorSetInfo = moverMarcadorInfo(marcador);
                animatorSetInfo.start();

                animatorSetInfo.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        infoGuideBinding.bocadillo.post(() -> {
                              float[] textPos = new float[2];
                              textPos[0] = infoGuideBinding.bocadillo.getX();
                              textPos[1] = infoGuideBinding.bocadillo.getY();

                              float[] imagePos = new float[2];
                              imagePos[0] = marcador.getX();
                              imagePos[1] = marcador.getY();

                              float startX = textPos[0] + infoGuideBinding.bocadillo.getWidth() / 2f;
                              float startY = textPos[1];
                              float endX = imagePos[0] + marcador.getWidth() / 2f;
                              float endY = imagePos[1]-25 + marcador.getHeight();

                            infoGuideBinding.arrowView.setArrowPosition(startX, startY, endX, endY);
                            });
                    }
                });

                marcador.setOnClickListener(v->{
                    showInfoDialog();
                    infoGuideBinding.arrowView.setVisibility(View.GONE);
                    marcador.setVisibility(View.GONE);
                    infoGuideBinding.btnNext.setVisibility(View.VISIBLE);
                    infoGuideBinding.btnNext.setOnClickListener(this::nextStep);

                });
                break;
            case 5:
                resumeGuideBinding = ResumeGuideBinding.inflate(getLayoutInflater(), binding.guideContainer, true);

                resumeGuideBinding.btnIniciarImg.setOnClickListener(this::skipGuide);

                break;
        }
    }

    private void animarTextoComenzar() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(welcomeGuideBinding.btnComenzarText, "scaleX", 1.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(welcomeGuideBinding.btnComenzarText, "scaleY", 1.5f, 1f);

        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    private AnimatorSet moverMarcadorMenu(View marcador, int opcion){

        float anchoIcono = (float)binding.navView.getWidth()/3;
        float altoIcono = (float)binding.navView.getHeight();

        float posicionY = binding.navView.getY();
        float posicionX = opcion*anchoIcono;

        return getAnimacionMarcador(marcador, posicionX, posicionY, anchoIcono, altoIcono);

    }

    private AnimatorSet moverMarcadorInfo(View marcador){
        View menu = binding.toolbar.findViewById(R.id.action_info);
        int[] location = new int[2];
        menu.getLocationOnScreen(location);

        float anchoIcono = menu.getWidth();
        float altoIcono = menu.getHeight();

        float posicionY = binding.toolbar.getY();


        return getAnimacionMarcador(marcador, location[0], posicionY, anchoIcono, altoIcono);

    }

    private AnimatorSet getAnimacionMarcador(View marcador, float posicionX, float posicionY, float anchoIcono, float altoIcono){

        //Ponemos el marcado tan grande como el icono a marcar
        ViewGroup.LayoutParams marcadorLayoutParams = marcador.getLayoutParams();
        marcadorLayoutParams.width = (int)anchoIcono;
        marcadorLayoutParams.height = (int)altoIcono;
        marcador.setLayoutParams(marcadorLayoutParams);

        ObjectAnimator moveX = ObjectAnimator.ofFloat(marcador, "x", posicionX);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(marcador, "y", posicionY);
        moveX.setDuration(1000);
        moveY.setDuration(1000);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(marcador, "scaleX", 1.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(marcador, "scaleY", 1.5f, 1f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(marcador, "alpha", 1f, 0.7f);

        scaleX.setRepeatCount(2);
        scaleY.setRepeatCount(2);
        fadeIn.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(moveX).with(moveY).before(scaleX).with(scaleY).with(fadeIn);
        animatorSet.setDuration(1000);

        return animatorSet;

    }

    private void pintarFlecha(ArrowView flecha, View marcador, View bocadillo) {

        bocadillo.post(() -> {
            float[] textPos = new float[2];
            textPos[0] = bocadillo.getX();
            textPos[1] = bocadillo.getY();

            float[] imagePos = new float[2];
            imagePos[0] = marcador.getX();
            imagePos[1] = marcador.getY();

            float startX = textPos[0] + bocadillo.getWidth() / 2f;
            float startY = textPos[1] + bocadillo.getHeight()-25;
            float endX = imagePos[0] + marcador.getWidth() / 2f;
            float endY = imagePos[1]-25;

            flecha.setArrowPosition(startX, startY, endX, endY);
        });
    }

    private void skipGuide(View view) {
        Preferences.setGuideCompleted(this, true);
        binding.guideContainer.setVisibility(View.GONE);
    }

    private void nextStep(View view) {
        currentStep++;
        showStep(currentStep);
    }


    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else
        if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }



}