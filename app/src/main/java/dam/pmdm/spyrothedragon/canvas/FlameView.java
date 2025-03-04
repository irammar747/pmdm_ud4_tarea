package dam.pmdm.spyrothedragon.canvas;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FlameView extends View {

    private List<Particle> particles;
    private Paint paint;
    private Random random;
    private Handler handler;
    private Runnable updateRunnable;

    public FlameView(Context context) {
        super(context);
        init();
    }

    public FlameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        particles = new ArrayList<>();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        random = new Random();

        // Runnable para actualizar la animación (~60fps)
        handler = new Handler(Looper.getMainLooper());
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateParticles();
                invalidate();
                handler.postDelayed(this, 16);
            }
        };
        handler.post(updateRunnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Dibujar cada partícula
        for (Particle p : particles) {
            paint.setColor(p.color);
            paint.setAlpha(p.alpha);
            canvas.drawCircle(p.x, p.y, p.size, paint);
        }
    }

    private void updateParticles() {
        // Actualizar partículas: mover y reducir opacidad
        Iterator<Particle> iter = particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            p.x += p.speedX;
            p.y += p.speedY;
            p.alpha -= 5;
            if (p.alpha <= 0) {
                iter.remove();
            }
        }
        // Agregar nuevas partículas para mantener la animación
        addParticle();
    }

    private void addParticle() {
        Particle p = new Particle();
        // Inicia en el centro horizontal y en la parte inferior de la vista (emitiendo desde la "boca")
        p.x = getWidth() / 2f;
        p.y = getHeight();
        p.size = random.nextInt(8) + 4;
        p.speedX = random.nextInt(7) - 3;  // velocidad horizontal entre -3 y 3
        p.speedY = -(random.nextInt(4) + 2); // velocidad vertical hacia arriba
        p.alpha = 255;
        // Color en tonos naranjas (variación en el verde)
        p.color = Color.argb(255, 255, random.nextInt(156), 0);
        particles.add(p);
    }

    // Clase interna que representa cada partícula
    private class Particle {
        float x, y;
        int size;
        int speedX, speedY;
        int alpha;
        int color;
    }
}