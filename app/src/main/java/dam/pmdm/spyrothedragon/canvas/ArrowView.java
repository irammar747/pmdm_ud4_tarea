package dam.pmdm.spyrothedragon.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import dam.pmdm.spyrothedragon.R;

public class ArrowView extends View {
    private float startX, startY, endX, endY;
    private Paint paint;
    private Path arrowHead;

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.yellow, getContext().getTheme())); // Color amarillo
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        arrowHead = new Path();
    }

    public void setArrowPosition(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        invalidate(); // Redibujar la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar la línea de la flecha
        canvas.drawLine(startX, startY, endX, endY, paint);

        // Dibujar la cabeza de la flecha
        float arrowSize = 80; // Tamaño de la cabeza
        float angle = (float) Math.atan2(endY - startY, endX - startX);

        float x1 = endX - arrowSize * (float) Math.cos(angle - Math.PI / 6);
        float y1 = endY - arrowSize * (float) Math.sin(angle - Math.PI / 6);
        float x2 = endX - arrowSize * (float) Math.cos(angle + Math.PI / 6);
        float y2 = endY - arrowSize * (float) Math.sin(angle + Math.PI / 6);

        arrowHead.reset();
        arrowHead.moveTo(endX, endY);
        arrowHead.lineTo(x1, y1);
        arrowHead.moveTo(endX, endY);
        arrowHead.lineTo(x2, y2);

        canvas.drawPath(arrowHead, paint);
    }
}
