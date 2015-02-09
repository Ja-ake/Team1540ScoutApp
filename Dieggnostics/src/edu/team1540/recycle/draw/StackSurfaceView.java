package edu.team1540.recycle.draw;

import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private SurfaceHolder holder;
	public final StackDrawer mainStackDrawer = new StackDrawer();
	public final StackDrawer oldStackDrawer = new StackDrawer();
	public SubmitDrawer submitDrawer = new SubmitDrawer(mainStackDrawer.stackHeight, oldStackDrawer.stackHeight, 70.f, 10.f);
	public SubmitDrawer oldSubmitDrawer;
	public final Stack<SubmitDrawer> oldSubmitDrawerStack = new Stack<SubmitDrawer>();

	public StackSurfaceView(final Context context) {
		super(context);
	}

	public StackSurfaceView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public StackSurfaceView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void surfaceCreated(final SurfaceHolder hold) {
		holder = hold;
		final Canvas canvas = holder.lockCanvas();
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
						Thread.sleep(1);
					} catch (final InterruptedException e) {
						run();
						break;
					}
					tthis.update();
				}
			}
		}.start();

		mainStackDrawer.x = 560.0f;
		mainStackDrawer.y = 280.0f;

		oldStackDrawer.x = 130.0f;
		oldStackDrawer.y = 280.0f;
	}

	@Override
	public boolean performClick() {
		return super.performClick();
		// return true;
	}

	@Override
	public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {

	}

	public void update() {
		final Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			return;
		}
		canvas.drawColor(Color.rgb(244, 244, 244));
		
		submitDrawer.mainStack = mainStackDrawer.stackHeight;
		submitDrawer.oldStack = oldStackDrawer.stackHeight;
		submitDrawer.mContainer = mainStackDrawer.container;
		submitDrawer.oContainer = oldStackDrawer.container;
		
		if (submitDrawer.mainStack < 0 || submitDrawer.mainStack > 5) submitDrawer.mainStack = 0;
		if (submitDrawer.oldStack < 0 || submitDrawer.oldStack > 5) submitDrawer.oldStack = 0;
		
		paint.setStrokeWidth(20);
		canvas.drawLine(210, 64, 480, 64, paint);
		canvas.drawLine(646, 64, 970, 64, paint);
		canvas.drawLine(1058, 148, 1058, 450, paint);
		canvas.drawLine(1058, 500, 1058, 1000, paint);
		paint.setStrokeWidth(1);
		paint.setTextSize(40);
		canvas.drawText("Submit", 500, 76, paint);
		canvas.drawText("Remove", 984, 488, paint);
		
		mainStackDrawer.draw(canvas, paint);
		oldStackDrawer.draw(canvas, paint);
		submitDrawer.draw(canvas, paint);
		
		if (oldSubmitDrawerStack.size() > 0) oldSubmitDrawerStack.peek().draw(canvas, paint);
		
		if (oldSubmitDrawer != null) {
			oldSubmitDrawer.reset();
			oldSubmitDrawer.draw(canvas, paint);
		}
		
		if (submitDrawer.x > 1000) {
			if (oldSubmitDrawer != null) oldSubmitDrawerStack.push(oldSubmitDrawer);
			
			oldSubmitDrawer = submitDrawer;
			oldSubmitDrawer.initX = 1000.f;
			oldSubmitDrawer.initY = 10.f;
			oldSubmitDrawer.x = 1000.f;
			oldSubmitDrawer.y = 10.f;
			mainStackDrawer.stackHeight = 0;
			oldStackDrawer.stackHeight = 0;
			mainStackDrawer.container = false;
			oldStackDrawer.container = false;
			submitDrawer = new SubmitDrawer(mainStackDrawer.stackHeight, oldStackDrawer.stackHeight, 70, 10);
		}
		
		if (oldSubmitDrawer != null) synchronized (oldSubmitDrawer) {
			if (oldSubmitDrawer.y > 1000) {
				if (oldSubmitDrawerStack.size() > 0) oldSubmitDrawer = oldSubmitDrawerStack.pop();
				else oldSubmitDrawer = null;
			}
		}

		holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceDestroyed(final SurfaceHolder holder) {

	}
}
