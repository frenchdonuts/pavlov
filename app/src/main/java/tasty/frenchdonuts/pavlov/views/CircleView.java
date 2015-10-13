package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: Implement this in XML?
 * Created by frenchdonuts on 1/7/15.
 */
public class CircleView extends View {
    private static final String TAG = CircleView.class.getSimpleName();

    private int color = red;
    private int level = 0;
    public static final int danger = Color.parseColor("#FFF44336");
    public static final int red = Color.parseColor("#DE000000");
    public static final int orange = Color.parseColor("#8A000000");
    public static final int yellow = Color.parseColor("#42000000");
    public static final int green = Color.parseColor("#42000000");
    public static final int blue = Color.parseColor("#1F000000");
    public static final int indigo = Color.parseColor("#1F000000");
    public static final int violet = Color.parseColor("#1F000000");

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint;
        int size = getWidth();
        int radius = getWidth() / 2;

        // Circle
        paint = new Paint();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawPaint(paint);
        paint.setColor(this.color);

        canvas.drawCircle(size / 2, size / 2, radius, paint);

        // Text
        paint = new Paint();
        float textSize = size * 0.66666f;

        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));

        // Don't ask why it is textSize/3...it's just what looks right
        canvas.drawText(Integer.toString(level), size / 2, size / 2 + textSize / 3, paint);
    }

    public void setPriority(int priority) {
        this.level = priority;
        switch (priority) {
            case 8:
                this.color = danger;
                break;
            case 7:
                this.color = red;
                break;
            case 6:
                this.color = orange;
                break;
            case 5:
                this.color = yellow;
                break;
            case 4:
                this.color = green;
                break;
            case 3:
                this.color = blue;
                break;
            case 2:
                this.color = indigo;
                break;
            case 1:
                this.color = violet;
                break;
            default:
                this.color = Color.WHITE;
                break;
        }
        invalidate();
    }
}
