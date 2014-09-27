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

	// 터치 좌표 기억

	// 거리값 저장용 쓰래기값 0번,마지막번 포함 5터치 =7
	double dist[] = new double[100];
	// 비교 저장용 쓰래기값 0번,마지막번 포함 5터치 =7
	private double pp[] = new double[100];

	// 이터 중복으로 들어가지 않도록 옵션.
	public static int stamp_flag = 0;

	// 스탬프의 터치 개수를 설정.
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

			// 데이터가 여러번 들어가지 않도록 , 터치가 다시 다운이벤트 발생시 다시 데이터 업이 가능하도록 트루로 바꿈
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
			// 터치된 값을 서버에 보내서 확인 하는 부분.
			if (mActivePointers.size() == 5) {
				switch (stamp_flag) {
				case 0:
					// 사용
					stampManager = new StampManager();
					stampManager.UseStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "", code_C, code_F); // 유저 이메일은 알아서 받아 오니까 사용하려는
													// 컴퍼니코드와 프렌차이저 코드만 긁어와서
													// iv.setImageResource(R.drawable.ic_stampmark_white_big_unscaled);
					break;

				case 1:
					// 등록
					stampManager = new StampManager();
					stampManager.AddStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "");

					break;
				case 2:
					// 취소
					stampManager = new StampManager();
					stampManager.CancelStamp(getContext(), (int) dist[0] + "",
							(int) dist[1] + "", (int) dist[2] + "",
							(int) dist[3] + "");
					break;
				}

			}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointers.remove(pointerId);
			// 포인터 갱신되게끔 하는 부분.
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
		// 터치영역 크기 저장
		
		
		/*
		// draw all pointers (test)
		for (int size = mActivePointers.size(), i = 0; i < size; i++) {
			PointF point = mActivePointers.valueAt(i);

			//각 포인트를 색으로 크게 보이도록 표시.
			//일반 사용자에게는 이것도 제거.
			if (point != null)
				mPaint.setColor(colors[i % 9]);
			canvas.drawCircle(point.x, point.y, SIZE, mPaint);

			// 각 포인트 아래 포인트 순서 표시
			// canvas.drawText(i + "번", point.x, point.y + 80, textPaint2);

			xpoint[i] = (point.x / w) * 100;
			ypoint[i] = (point.y / h) * 100;


			// 각 좌표를 터치 뷰의 크기를 받아서 비례하여 계산하기 위하여 나누어준다. 그리고 비교를 위하여 *100을 해준다.

		}
		*/
		
		// // 스탬프 찾는 부분 /////

		// // 가장 먼 거리 구하기.////
		//0710 문득 생각난 아이디어인데 굳이 0123번을 그대로 받아서 값을 입력하지 말고 
		//순서대로 정렬후 다르게 받아들이면 그것도 보안에 도움이 될듯.
		

		for (int i = 0; i < stampnum; i++) {
			for (int j = 0; j < stampnum; j++) {
				// 두 점간의 거리 구하는 공식
				pp[j] = Math.sqrt((xpoint[i] - xpoint[j])
						* (xpoint[i] - xpoint[j]) + (ypoint[i] - ypoint[j])
						* (ypoint[i] - ypoint[j]));
			}

			// 터치값을 비교하여 가장 먼 거리값을 pp[]에 넣는다.
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

			// 이부분에 이제 각해상도별로 몇이상은 값을 추가하고 말고를 정해야한다.
			// 이건 포문 밖에 있는게 맞는거 같은데.. 나중에 수정하자.
			
			// 해상도에 따라 dist 값을 수정한다.
			
			// 처음에 DPI 갑으로 나눈다.
			// 그 후 XDPI 값을  비교하여 다시 세분화 해준다.
			
			switch (TouchActivity.densityDpi) {
			case 213:
				
				
				if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels < 1300) {
					// 노트 8.0
					dist[i] = Math.round(pp[0] * 3.1);
					// 반올림 한다.
				}
				
				

				break;
			case 240:
				
				if (800 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 850) {
					// 갤2
					// 800
					//dpi 240
					dist[i] = Math.round(pp[0] * 1.61);
					// dist[i] = dist[i] * 1.11;
				}

				
				break;
			case 320:
				
				if(260 <= (int)TouchActivity.xdpi && (int)TouchActivity.xdpi <= 270){
		
					if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1300) {
						// 갤 노트2
						// 갤 노트 2의 2배 값을 기준으로 한다.
						dist[i] = pp[0] * 2;
					}
				}
				
				if(300 <= (int)TouchActivity.xdpi && (int)TouchActivity.xdpi <= 310){
					
					if (1250 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1300) {
						// 갤3
						
						dist[i] = Math.round(pp[0] * 1.7);
					}
				}

				break;
			case 480:
				
				
				if (1800 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1850) {
					// dpi 320
					// Gpro2
					dist[i] = Math.round(pp[0] * 2.1);
					// 반올림 한다.
				}
				if (1900 <= TouchActivity.heightPixels && TouchActivity.heightPixels <= 1950) {
					// 갤5
					dist[i] = Math.round(pp[0] * 1.8234);
					// 반올림 한다.
				}

				break;

			default:
				break;
			}
			
			
/*
		
			if (1550 < Resolution.h && Resolution.h < 1570) {
				// 갤4
				dist[i] = Math.round(pp[0] * 1.765);
				// 반올림 한다.
			}
		
*/
			/*
			 * 
			 * =스마트폰= 노트2 - H=1034 30/29 1.11 갤4 - H=1550 72/60 0
			 * 
			 * =태블릿= 노트10.1 - H=1126 30/29 2.39
			 * 
			 * DPI 값 
			 * 갤2 240
			 * 노트2 320
			 * Gpro2 480
			 * 노트8.0 213
			 * 
			 */

		}

		// // 같은 값이 있으면 0으로 바꾸는 과정 // //
		// 이 과정은 수정하여 더 좋은 알고리즘을 만들어도 좋다.
		
		
		// 같은 값이 있으면 0으로 바꾸기 전 내림차순 정렬
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

		// 같은 값이 있으면 0으로 바꿈.
		for (int i = 0; i < stampnum; i++) {
			if (dist[i] == dist[i + 1]) {
				dist[i + 1] = 0;
				i = i + 1;
			}
		}

		// 거리값을 내림차순으로 다시 정렬
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

		// 지금 터치되는 수를 나타냄, 벨류 뷰로 보내기 위해서 변수 선언후 다시 받음. (test)

//		canvas.drawText("멀티터치 뷰", 10, 20, textPaint);
//		canvas.drawText("Touch View Width:" + w, 10, 40, textPaint);
//		canvas.drawText("Touch View Height :" + h, 10, 60, textPaint);
//		canvas.drawText("Activity Width :" + TouchActivity.widthPixels, 10, 80, textPaint);
//		canvas.drawText("Activity Height :" + TouchActivity.heightPixels, 10, 100, textPaint);
//		canvas.drawText("터치되는 수 :" + mActivePointers.size(), 10, 120, textPaint);
//
//		canvas.drawText("1번" + (int) dist[0], 10, 160, textPaint);
//		canvas.drawText("2번" + (int) dist[1], 10, 180, textPaint);
//		canvas.drawText("3번" + (int) dist[2], 10, 200, textPaint);
//		canvas.drawText("4번" + (int) dist[3], 10, 220, textPaint);
//		
//		canvas.drawText("DPI값 : " + (int) TouchActivity.densityDpi, 10, 260, textPaint);
//		canvas.drawText("scaledDensity값 : " + TouchActivity.scaledDensity, 10, 280, textPaint);
//		canvas.drawText("density값 : " + TouchActivity.density, 10, 300, textPaint);
//		canvas.drawText("xdpi값 : " + TouchActivity.xdpi, 10, 320, textPaint);
//		canvas.drawText("ydpi값 : " + TouchActivity.ydpi, 10, 340, textPaint);

	}

}