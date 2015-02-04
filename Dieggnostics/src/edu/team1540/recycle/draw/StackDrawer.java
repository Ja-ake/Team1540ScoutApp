package edu.team1540.recycle.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class StackDrawer {
	public int stackHeight = 0;
	public float x, y;

	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);
		
		float yshift = 0.0f;
		float dy = 100.0f;
		if (dy < 0.0d) throw new AssertionError("dy is negative or zero.");
		for (int i=0; i<7; i++) {
			if (i == 7-stackHeight) paint.setStyle(Style.FILL_AND_STROKE);
			canvas.drawRect(x, y+yshift, x+350.0f, y+yshift+dy-5.0f, paint);
			yshift += dy;
		}
	}
}
