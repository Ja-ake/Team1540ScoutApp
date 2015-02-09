package edu.team1540.recycle.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class SubmitDrawer {
	public int mainStack, oldStack;
	public boolean mContainer = false, oContainer = false;
	public float x, y;
	public float initX, initY;
	
	public boolean beingMoved;
	
	public SubmitDrawer(int m, int o, float x_, float y_) {
		mainStack = m;
		oldStack = o;
		x = x_;
		y = y_;
		initX = x; initY = y;
	}
	
	public void draw(Canvas canvas, Paint paint) {		
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setColor(Color.WHITE);
		canvas.drawRect(x, y, x+110, y+110, paint);

		paint.setStyle(Style.STROKE);
		paint.setColor(Color.BLACK);
		canvas.drawRect(x, y, x+110, y+110, paint);
		
		paint.setTextSize(64.f);
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		canvas.drawText(oldStack + "", 16+x, 100+y, paint);
		canvas.drawText(mainStack + "", 56+x, 100+y, paint);
		
		if (mContainer) canvas.drawCircle(x+74, y+36, 8, paint);
		if (oContainer) canvas.drawCircle(x+34, y+36, 8, paint);
	}
	
	public void reset() {
		if (!beingMoved) {
			x = initX;
			y = initY;
		}
		
		if (x < initX) x = initX;
		if (y < initY) y = initY;
	}
}
