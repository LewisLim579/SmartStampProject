package com.smartstamp.tab.press;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.smartstamp.database.StampManager;

public class MultitouchView extends View {

	private static final int SIZE = 60;

	Context mContext;

	// private static final String Context = null;

	private SparseArray<PointF> mActivePointers;
	private Paint mPaint;
	private int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA,
			Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
			Color.LTGRAY, Color.YELLOW };

	private Paint textPaint;
	private Paint textPaint2;
	private Boolean stampDown = false;
	
	double xpoint[] = new double[10];
	double ypoint[] = new double[10];

	// ��ġ ��ǥ ���

	// �Ÿ��� ����� �����Ⱚ 0��,�������� ���� 5��ġ =7
	double dist[] = new double[100];
	// �� ����� �����Ⱚ 0��,�������� ���� 5��ġ =7
	private double pp[] = new double[100];

	// ���� �ߺ����� ���� �ʵ��� �ɼ�.
	public static int stamp_flag = 0;

	// �������� ��ġ ������ ����.
	private int stampnum = 5;

	public static String code_C = "";
	public static String code_F = "";

	public MultitouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initView();
	}

	private void initView() {
		mActivePointers = new SparseArray<PointF>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// set painter color to a color you like
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextSize(20);
		textPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint2.setTextSize(30);
		textPaint2.setColor(Color.BLACK);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();

		// StampManager
		StampManager stampManager;
		switch (maskedAction) {

		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			if (mActivePointers.size() == 0) {
				stampDown = false;
			}

			if (!stampDown) {
				PointF f = new PointF();
				f.x = event.getX(pointerIndex);
				f.y = event.getY(pointerIndex);

				mActivePointers.put(pointerId, f);
				if (mActivePointers.size() == 5) {
					stampDown = true;
				}
			}

			// �����Ͱ� ������ ���� �ʵ��� , ��ġ�� �ٽ� �ٿ��̺�Ʈ �߻��� �ٽ� ������ ���� �����ϵ��� Ʈ��� �ٲ�
			break;
		}
		case MotionEvent.ACTION_MOVE: { // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_POINTER_UP:
			// ��ġ�� ���� ������ ������ Ȯ�� �ϴ� �κ�.
			if (mActivePointers.size() == 5) {
				switch (stamp_flag) {
				case 0:
					// ���
					stampManager = new StampManager();
					stampManager.UseStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "", code_C, code_F); // ���� �̸����� �˾Ƽ� �޾� ���ϱ� ����Ϸ���
													// ���۴��ڵ�� ���������� �ڵ常 �ܾ�ͼ�
													// iv.setImageResource(R.drawable.ic_stampmark_white_big_unscaled);
					break;

				case 1:
					// ���
					stampManager = new StampManager();
					stampManager.AddStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "");

					break;
				case 2:
					// ���
					stampManager = new StampManager();
					stampManager.CancelStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "");
					break;
				}

			}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointers.remove(pointerId);
			// ������ ���ŵǰԲ� �ϴ� �κ�.
			break;
		}
		}
		invalidate();

		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int w = getWidth();
		int h = getHeight();
		// ��ġ���� ũ�� ����
		
		
		/*
		// draw all pointers (test)
		for (int size = mActivePointers.size(), i = 0; i < size; i++) {
			PointF point = mActivePointers.valueAt(i);

			//�� ����Ʈ�� ������ ũ�� ���̵��� ǥ��.
			//�Ϲ� ����ڿ��Դ� �̰͵� ����.
			if (point != null)
				mPaint.setColor(colors[i % 9]);
			canvas.drawCircle(point.x, point.y, SIZE, mPaint);

			// �� ����Ʈ �Ʒ� ����Ʈ ���� ǥ��
			// canvas.drawText(i + "��", point.x, point.y + 80, textPaint2);

			xpoint[i] = (point.x / w) * 100;
			ypoint[i] = (point.y / h) * 100;


			// �� ��ǥ�� ��ġ ���� ũ�⸦ �޾Ƽ� ����Ͽ� ����ϱ� ���Ͽ� �������ش�. �׸��� �񱳸� ���Ͽ� *100�� ���ش�.

		}
		*/
		
		// // ������ ã�� �κ� /////

		// // ���� �� �Ÿ� ���ϱ�.////
		//0710 ���� ������ ���̵���ε� ���� 0123���� �״�� �޾Ƽ� ���� �Է����� ���� 
		//������� ������ �ٸ��� �޾Ƶ��̸� �װ͵� ���ȿ� ������ �ɵ�.
		

		for (int i = 0; i < stampnum; i++) {
			for (int j = 0; j < stampnum; j++) {
				// �� ������ �Ÿ� ���ϴ� ����
				pp[j] = Math.sqrt((xpoint[i] - xpoint[j])
						* (xpoint[i] - xpoint[j]) + (ypoint[i] - ypoint[j])
						* (ypoint[i] - ypoint[j]));
			}

			// ��ġ���� ���Ͽ� ���� �� �Ÿ����� pp[]�� �ִ´�.
			double temp = 0;
			for (int n = 0; n < stampnum; n++) {
				for (int m = 0; m < stampnum; m++) {
					if (pp[m] < pp[m + 1]) {
						temp = pp[m + 1];
						pp[m + 1] = pp[m];
						pp[m] = temp;

					}

				}
			}

			// �̺κп� ���� ���ػ󵵺��� ���̻��� ���� �߰��ϰ� ���� ���ؾ��Ѵ�.
			// �̰� ���� �ۿ� �ִ°� �´°� ������.. ���߿� ��������.
			
			// �ػ󵵿� ���� dist ���� �����Ѵ�.
			
			// ó���� DPI ������ ������.
			// �� �� XDPI ����  ���Ͽ� �ٽ� ����ȭ ���ش�.
			
			switch (TouchActivity.densityDpi) {
			case 213:
				
				
				if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels < 1300) {
					// ��Ʈ 8.0
					dist[i] = Math.round(pp[0] * 3.1);
					// �ݿø� �Ѵ�.
				}
				
				

				break;
			case 240:
				
				if (800 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 850) {
					// ��2
					// 800
					//dpi 240
					dist[i] = Math.round(pp[0] * 1.61);
					// dist[i] = dist[i] * 1.11;
				}

				
				break;
			case 320:
				
				if(260 <= (int)TouchActivity.xdpi && (int)TouchActivity.xdpi <= 270){
		
					if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1300) {
						// �� ��Ʈ2
						// �� ��Ʈ 2�� 2�� ���� �������� �Ѵ�.
						dist[i] = pp[0] * 2;
					}
				}
				
				if(300 <= (int)TouchActivity.xdpi && (int)TouchActivity.xdpi <= 310){
					
					if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1300) {
						// ��3
						
						dist[i] = Math.round(pp[0] * 1.7);
					}
				}

				break;
			case 480:
				
				
				if (1800 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1850) {
					// dpi 320
					// Gpro2
					dist[i] = Math.round(pp[0] * 2.1);
					// �ݿø� �Ѵ�.
				}
				if (1900 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1950) {
					// ��5
					dist[i] = Math.round(pp[0] * 1.8234);
					// �ݿø� �Ѵ�.
				}

				break;

			default:
				break;
			}
			
			
/*
		
			if (1550 < Resolution.h && Resolution.h < 1570) {
				// ��4
				dist[i] = Math.round(pp[0] * 1.765);
				// �ݿø� �Ѵ�.
			}
		
*/
			/*
			 * 
			 * =����Ʈ��= ��Ʈ2 - H=1034 30/29 1.11 ��4 - H=1550 72/60 0
			 * 
			 * =�º�= ��Ʈ10.1 - H=1126 30/29 2.39
			 * 
			 * DPI �� 
			 * ��2 240
			 * ��Ʈ2 320
			 * Gpro2 480
			 * ��Ʈ8.0 213
			 * 
			 */

		}

		// // ���� ���� ������ 0���� �ٲٴ� ���� // //
		// �� ������ �����Ͽ� �� ���� �˰����� ���� ����.
		
		
		// ���� ���� ������ 0���� �ٲٱ� �� �������� ����
		double temp1 = 0;
		for (int i = 0; i < stampnum; i++) {
			for (int j = 0; j < stampnum; j++) {
				if (dist[j] < dist[j + 1]) {
					temp1 = dist[j + 1];
					dist[j + 1] = dist[j];
					dist[j] = temp1;

				}

			}
		}

		// ���� ���� ������ 0���� �ٲ�.
		for (int i = 0; i < stampnum; i++) {
			if (dist[i] == dist[i + 1]) {
				dist[i + 1] = 0;
				i = i + 1;
			}
		}

		// �Ÿ����� ������������ �ٽ� ����
		double temp = 0;
		for (int i = 0; i < stampnum; i++) {
			for (int j = 0; j < stampnum; j++) {
				if (dist[j] < dist[j + 1]) {
					temp = dist[j + 1];
					dist[j + 1] = dist[j];
					dist[j] = temp;

				}

			}
		}

		// ���� ��ġ�Ǵ� ���� ��Ÿ��, ���� ��� ������ ���ؼ� ���� ������ �ٽ� ����. (test)

//		canvas.drawText("��Ƽ��ġ ��", 10, 20, textPaint);
//		canvas.drawText("Touch View Width:" + w, 10, 40, textPaint);
//		canvas.drawText("Touch View Height :" + h, 10, 60, textPaint);
//		canvas.drawText("Activity Width :" + TouchActivity.widthPixels, 10, 80, textPaint);
//		canvas.drawText("Activity Height :" + TouchActivity.heightPixels, 10, 100, textPaint);
//		canvas.drawText("��ġ�Ǵ� �� :" + mActivePointers.size(), 10, 120, textPaint);
//
//		canvas.drawText("1��" + (int) dist[0], 10, 160, textPaint);
//		canvas.drawText("2��" + (int) dist[1], 10, 180, textPaint);
//		canvas.drawText("3��" + (int) dist[2], 10, 200, textPaint);
//		canvas.drawText("4��" + (int) dist[3], 10, 220, textPaint);
//		
//		canvas.drawText("DPI�� : " + (int) TouchActivity.densityDpi, 10, 260, textPaint);
//		canvas.drawText("scaledDensity�� : " + TouchActivity.scaledDensity, 10, 280, textPaint);
//		canvas.drawText("density�� : " + TouchActivity.density, 10, 300, textPaint);
//		canvas.drawText("xdpi�� : " + TouchActivity.xdpi, 10, 320, textPaint);
//		canvas.drawText("ydpi�� : " + TouchActivity.ydpi, 10, 340, textPaint);

	}

}