package edu.team1540.recycle.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public StackSurfaceView(Context context) {
		super(context);
	}
	
	public StackSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public StackSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    Canvas canvas = holder.lockCanvas();
	    canvas.drawColor(Color.WHITE);
	    paint.setColor(Color.BLUE);
	    canvas.drawCircle(100, 200, 50, paint);
	    holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}
