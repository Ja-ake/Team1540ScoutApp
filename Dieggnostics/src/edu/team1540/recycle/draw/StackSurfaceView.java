package edu.team1540.recycle.draw;

import java.util.logging.Logger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private SurfaceHolder holder;
	private StackDrawer mainStackDrawer = new StackDrawer();
	private StackDrawer oldStackDrawer = new StackDrawer();
	
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
	public void surfaceCreated(SurfaceHolder hold) {
		holder = hold;
	    Canvas canvas = holder.lockCanvas();
	    canvas.drawColor(Color.WHITE);
	    paint.setColor(Color.BLUE);
	    canvas.drawCircle(100, 200, 50, paint);
	    holder.unlockCanvasAndPost(canvas);
	    
	    final StackSurfaceView tthis = this;
	    new Thread() {
	    	@Override
	    	public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						run();
						break;
					}
					tthis.update();
				}
	    	}
	    }.start();
	    
	    mainStackDrawer.x = 500.0f;
	    mainStackDrawer.y = 330.0f;
	    
	    oldStackDrawer.x = 50.0f;
	    oldStackDrawer.y = 330.0f;
	    oldStackDrawer.stackHeight = 1;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}
	
	public void update() {
	    Canvas canvas = holder.lockCanvas();
	    canvas.drawColor(Color.WHITE);
		mainStackDrawer.draw(canvas, paint);
		oldStackDrawer.draw(canvas, paint);
	    holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}
