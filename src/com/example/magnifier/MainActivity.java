package com.example.magnifier;
/**
 * 
 * �Ŵ�Ч��
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
		//�Ŵ󾵵İ뾶
		private final int r = 80;
		//�Ŵ󾵵ı���
		private final int f = 2;
		private Matrix matrix = new Matrix();
		//�Ŵ󾵵�λͼ
		private Bitmap bm;
		//�Ŵ󾵵���߾�
		private int left = 0;
		//�Ŵ󾵵Ķ��߾�
		private int right = 0;
		public myView(Context context) {

			super(context);
			Bitmap bm_s = BitmapFactory.decodeResource(getResources(), R.drawable.myself);
			bitmap = bm_s;
			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bm_s, bm_s.getWidth()*f, bm_s.getHeight()*f, true), TileMode.CLAMP, TileMode.CLAMP);
			//Բ�ε�drawable
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
			//���Ʊ���ͼ��
			canvas.drawBitmap(bitmap, 0,0, null);
			//���ƷŴ�
			canvas.drawBitmap(bm, left,right, null);
			//���ƷŴ���ͼ��
			drawable.draw(canvas);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			//��ȡ��ǰ�������x��
			final int x = (int)event.getX();
			//��ȡy��
			final int y = (int)event.getY();
			matrix.setTranslate(r-x*f, r-y*f);
			drawable.getPaint().getShader().setLocalMatrix(matrix);
			//����Բ�����о���
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
