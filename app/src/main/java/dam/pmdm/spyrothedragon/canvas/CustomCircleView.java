package dam.pmdm.spyrothedragon.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomCircleView extends View {
    private Paint paintCircle, paintArrow;
    private Path arrowPath;

    public CustomCircleView(Context context) {
        super(context);
        init();
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paintCircle = new Paint();
        paintCircle.setColor(Color.parseColor("#800000FF"));
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setAntiAlias(true);

        paintArrow = new Paint();
        paintArrow.setColor(Color.WHITE);
        paintArrow.setStyle(Paint.Style.STROKE);
        paintArrow.setStrokeWidth(8);
        paintArrow.setAntiAlias(true);

        arrowPath = new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar el círculo
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) / 2.5f;
        canvas.drawCircle(cx, cy, radius, paintCircle);

        // Dibujar la flecha
        arrowPath.reset();
        arrowPath.moveTo(cx - 30, cy + 40);
        arrowPath.lineTo(cx, cy + 80);
        arrowPath.lineTo(cx + 30, cy + 40);
        canvas.drawPath(arrowPath, paintArrow);
    }
}
