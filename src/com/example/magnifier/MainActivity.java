package com.example.magnifier;
/**
 * 
 * 放大镜效果
 * 
 * **/
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private FrameLayout frame = null;
	private Paint paint = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		frame = (FrameLayout)findViewById(R.id.frame);
		frame.addView(new myView(this));
	}
	@SuppressLint("DrawAllocation")
	private class myView extends View{

		private Bitmap bitmap;
		private ShapeDrawable drawable;
		//放大镜的半径
		private final int r = 80;
		//放大镜的倍数
		private final int f = 2;
		private Matrix matrix = new Matrix();
		//放大镜的位图
		private Bitmap bm;
		//放大镜的左边距
		private int left = 0;
		//放大镜的顶边距
		private int right = 0;
		public myView(Context context) {

			super(context);
			Bitmap bm_s = BitmapFactory.decodeResource(getResources(), R.drawable.myself);
			bitmap = bm_s;
			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bm_s, bm_s.getWidth()*f, bm_s.getHeight()*f, true), TileMode.CLAMP, TileMode.CLAMP);
			//圆形的drawable
			drawable = new ShapeDrawable(new OvalShape());
			drawable.getPaint().setShader(shader);
			drawable.setBounds(0, 0, r*2, r*2);
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
			left = r - bm.getWidth()/2;
			right = r - bm.getHeight()/2;
		}

		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			//绘制背景图像
			canvas.drawBitmap(bitmap, 0,0, null);
			//绘制放大镜
			canvas.drawBitmap(bm, left,right, null);
			//绘制放大后的图像
			drawable.draw(canvas);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			//获取当前触摸点的x轴
			final int x = (int)event.getX();
			//获取y轴
			final int y = (int)event.getY();
			matrix.setTranslate(r-x*f, r-y*f);
			drawable.getPaint().getShader().setLocalMatrix(matrix);
			//设置圆的外切矩形
			drawable.setBounds(x-r, y-r, x+r, y+r);
			left = x-bm.getWidth()/2;
			right = y - bm.getHeight()/2;
			invalidate();
			return true;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
