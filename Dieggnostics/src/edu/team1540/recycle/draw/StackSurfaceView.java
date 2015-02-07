package edu.team1540.recycle.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class StackSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private SurfaceHolder holder;
	public final StackDrawer mainStackDrawer = new StackDrawer();
	public final StackDrawer oldStackDrawer = new StackDrawer();

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
						Thread.sleep(100);
					} catch (final InterruptedException e) {
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

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View v, final MotionEvent event) {
				mainStackDrawer.stackHeight = 3;
				v.performClick();
				return true;
			}
		});
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
		canvas.drawColor(Color.WHITE);
		mainStackDrawer.draw(canvas, paint);
		oldStackDrawer.draw(canvas, paint);
		holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceDestroyed(final SurfaceHolder holder) {

	}
}
