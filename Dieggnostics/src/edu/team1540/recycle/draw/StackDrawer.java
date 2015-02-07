package edu.team1540.recycle.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class StackDrawer {
	public int stackHeight = 0;
	public float x, y;

	public void draw(final Canvas canvas, final Paint paint) {
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);

		float yshift = 0.0f;
		final float dy = 150.0f;
		for (int i = 0; i < 5; i++) {
			if (i == 5 - stackHeight) {
				paint.setStyle(Style.FILL_AND_STROKE);
			}
			canvas.drawRect(x, y + yshift, x + 350.0f, y + yshift + dy - 5.0f, paint);
			yshift += dy;
		}
		
		if (stackHeight > 5 || stackHeight < 0) stackHeight = 0;
	}
}
