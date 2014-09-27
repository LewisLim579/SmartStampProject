package com.smartstamp.tab.press;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.smartstamp.BackPressCloseHandler;
import com.smartstamp.R;

public class TouchActivity extends Activity {

	private BackPressCloseHandler backPressCloseHandler;
	static final int DIALOG_CUSTOM_ID = 0;
	int count = 0;
	ImageView i1, i2;
	ToggleButton tb;
	static Integer[] coupons = { R.id.coupon1, R.id.coupon2, R.id.coupon3,
			R.id.coupon4, R.id.coupon5, R.id.coupon6, R.id.coupon7,
			R.id.coupon8, R.id.coupon9, R.id.coupon10 };
	
	static  int densityDpi;
	static int heightPixels;
	static int widthPixels;
	static float scaledDensity;
	static float density;
	static float xdpi;
	static float ydpi;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.touch);
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		DisplayMetrics metrics = new DisplayMetrics();
		  getWindowManager().getDefaultDisplay().getMetrics(metrics);
		  heightPixels = metrics.heightPixels;
		  widthPixels = metrics.widthPixels;
		 densityDpi = metrics.densityDpi;
		  density = metrics.density;
		  scaledDensity = metrics.scaledDensity;
		  xdpi = metrics.xdpi;
		 ydpi = metrics.ydpi;
		
		  
		  
		/*
		 * Toast.makeText(getApplication(),
		 * bbb.get(0).get("company_code")+" | "+
		 * bbb.get(0).get("franchise_code")+
		 * " | "+bbb.get(0).get("time")+" | "+bbb
		 * .get(0).get("count(company_code)"), 1).show();
		 * Toast.makeText(getApplication(),
		 * bbb.get(3).get("company_code")+" | "+
		 * bbb.get(3).get("franchise_code")+
		 * " | "+bbb.get(3).get("time")+" | "+bbb
		 * .get(3).get("count(company_code)"), 1).show();
		 */

		/*
		 * //////////// 리스트 불러오기////////////// DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //디비 선언을 하고
		 * ArrayList<HashMap<String, String>> stamplist = db.getStampList(); //
		 * 디비에서 스탬프 리스트를 불러옴.
		 * 
		 * int listlength = stamplist.size(); //리스트에 나타날 지점 개수를 표시함.
		 * Toast.makeText(getApplication(), ""+listlength, 0).show(); // 리스트를
		 * 나타날 것을 보임. // 처음에 정렬할때 시간순으로 정렬해서 0번이 가장 최근에 추가된 스탬프 // 즉 제일 위에 존재해도
		 * 좋음 // 가장 최근에 적립한 스탬프가 제일 위에 보이게 하는거지.
		 * 
		 * for(int i = 0 ; i<listlength;i++){ Toast.makeText(getApplication(),
		 * stamplist
		 * .get(i).get("company_code")+" | "+stamplist.get(i).get("franchise_code"
		 * )+" | "+stamplist.get(i).get("time")+" | "+stamplist.get(i).get(
		 * "count(company_code)"
		 * )+" | "+stamplist.get(i).get("SUM(coupon_stamp)"), 0).show(); }
		 * 
		 * // 이건 보면 알겠지 ???
		 */

		// ////////프렌차이저 정보 보기////////////////////
		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //디비 선언을 하고 ArrayList<HashMap<String, String>> franchise =
		 * db.getFranchise_used();
		 * 
		 * int franchiselength = franchise.size();
		 * 
		 * 
		 * Toast.makeText(getApplication(), ""+franchiselength, 0).show();
		 * 
		 * for(int i = 0 ; i<franchiselength;i++){
		 * Toast.makeText(getApplication(),
		 * franchise.get(i).get("company_code")+
		 * " | "+franchise.get(i).get("franchise_code"
		 * )+" | "+franchise.get(i).get
		 * ("franchise_name")+" | "+franchise.get(i).
		 * get("distinct_stamp1")+" | "
		 * +franchise.get(i).get("distinct_stamp2")+
		 * " | "+franchise.get(i).get("franchise_location1"
		 * )+" | "+franchise.get(
		 * i).get("franchise_location1")+" | "+franchise.get
		 * (i).get("address")+" | "+franchise.get(i).get("phone_num"),
		 * 0).show(); }
		 */// 트롤홍을위한것

		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //디비 선언을 하고 ArrayList<HashMap<String, String>> comapny =
		 * db.getCompany_all();
		 * 
		 * 
		 * 
		 * int companylength = comapny.size();
		 * 
		 * Toast.makeText(getApplication(), ""+companylength, 0).show();
		 * 
		 * for(int i = 0 ; i<companylength;i++){
		 * Toast.makeText(getApplication(),
		 * comapny.get(i).get("company_code")+" | "
		 * +comapny.get(i).get("company_name"), 0).show(); }
		 */

		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //디비 선언을 하고 ArrayList<HashMap<String, String>> franchise_all =
		 * db.getFranchise_all();
		 * 
		 * int franchise_all_length = franchise_all.size();
		 * 
		 * Toast.makeText(getApplication(), ""+franchise_all_length, 0).show();
		 * 
		 * for(int i = 0 ; i<franchise_all_length;i++){
		 * Toast.makeText(getApplication(),
		 * franchise_all.get(i).get("company_code"
		 * )+" | "+franchise_all.get(i).get
		 * ("franchise_code")+" | "+franchise_all
		 * .get(i).get("franchise_name")+" | "
		 * +franchise_all.get(i).get("distinct_stamp1")+" | "
		 * +franchise_all.get(
		 * i).get("distinct_stamp2")+" | "+franchise_all.get(i
		 * ).get("franchise_location1"
		 * )+" | "+franchise_all.get(i).get("franchise_location2"), 0).show(); }
		 */

		/*
		 * // 자주 방문하는 프렌차이즈 내림차순
		 * 
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //디비 선언을 하고 ArrayList<HashMap<String, String>> TopCompany =
		 * db.getTopCompany();
		 * 
		 * for(int i = 0 ; i<TopCompany.size();i++){
		 * Toast.makeText(getApplication(),
		 * TopCompany.get(i).get("company_code")
		 * +" | "+TopCompany.get(i).get("franchise_code"
		 * )+" | "+TopCompany.get(i)
		 * .get("time")+" | "+TopCompany.get(i).get("count(company_code)"),
		 * 0).show(); }
		 */

		/*
		 * //자주 방문하는 매장 오름 차순으로 되어있어서 포문 돌릴때 거꾸로 돌려야함 TopFranchise.size부터 시작해서
		 * -- 하길.. DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //디비 선언을 하고
		 * ArrayList<HashMap<String, String>> TopFranchise =
		 * db.getTopFranchise();
		 * 
		 * for(int i = 0 ; i<TopFranchise.size();i++){
		 * Toast.makeText(getApplication(),
		 * TopFranchise.get(i).get("company_code"
		 * )+" | "+TopFranchise.get(i).get(
		 * "franchise_code")+" | "+TopFranchise.get
		 * (i).get("time")+" | "+TopFranchise.get(i).get("count(company_code)"),
		 * 0).show(); }
		 */

		/*
		 * //쿠폰 사용내역 가장 최근에 사용한 내역이 제일 처음 0번. DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //디비 선언을 하고
		 * ArrayList<HashMap<String, String>> stampused = db.getStamp_used();
		 * 
		 * for(int i = 0 ; i<stampused.size();i++){
		 * Toast.makeText(getApplication(),
		 * stampused.get(i).get("email")+" | "+stampused
		 * .get(i).get("company_code"
		 * )+" | "+stampused.get(i).get("franchise_code"
		 * )+" | "+stampused.get(i).
		 * get("coupon_stamp")+" | "+stampused.get(i).get
		 * ("flag")+" | "+stampused.get(i).get("time"), 0).show(); }
		 */

		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //디비 선언을 하고 ArrayList<HashMap<String, String>> stamplist =
		 * db.getStampDetails(); // 디비에서 스탬프 리스트를 불러옴.
		 * 
		 * 
		 * //리스트에 나타날 지점 개수를 표시함.
		 * 
		 * 
		 * for(int i = 0 ; i<stamplist.size();i++){
		 * Toast.makeText(getApplication(),
		 * stamplist.get(i).get("company_code")+
		 * " | "+stamplist.get(i).get("franchise_code"
		 * )+" | "+stamplist.get(i).get("time"), 0).show(); }
		 */

		/*
		 * //스템프 사용하는 부분. StampManager stampmanager = new StampManager();
		 * stampmanager.UseStamp(getApplicationContext(),company_code,
		 * franchise_code); //유저 이메일은 알아서 받아 오니까 사용하려는 컴퍼니코드와 프렌차이저 코드만 긁어와서
		 * 넣어주면 됨!
		 * 
		 * //스탬프 취소하는 부분 StampManager stampmanager = new StampManager();
		 * stampmanager.CancelStamp(getApplicationContext(),company_code,
		 * franchise_code); //유저 이메일은 알아서 받아 오니까 사용하려는 컴퍼니코드와 프렌차이저 코드만 긁어와서
		 * 넣어주면 됨!
		 * 
		 * 
		 * //둘다 서버에서 이루어지는 과정은 //디바이스에서 서버로 email,company_code,franchise_code를
		 * 보내고 그걸 가지고 로그를 -10, -1 로 추가하는 것이다.
		 */

		/*
		 * double w = getWindowManager().getDefaultDisplay().getWidth(); double
		 * h = getWindowManager().getDefaultDisplay().getHeight(); 디바이스의 해상도
		 * 구하기.
		 */
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.TouchView);
		View vw = new MultitouchView(getApplicationContext(), null);

		rl.addView(vw);

		
		

		tb = (ToggleButton) findViewById(R.id.toggleButton1);
		tb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tb.isChecked()) {
					MultitouchView.stamp_flag = 2;
				} else {
					MultitouchView.stamp_flag = 1;
				}

			}
		});

//		Button b = (Button) findViewById(R.id.used_button);
//		Log.i("tab1", R.id.touch_background + "");
//		b.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//
//				if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//					ImageView backimage = (ImageView) findViewById(R.id.touch_background);
//					backimage
//							.setImageResource(R.drawable.ic_stampmark_red_big_unscaled);
//				}
//				if (event.getActionMasked() == MotionEvent.ACTION_UP) {
//					ImageView backimage = (ImageView) findViewById(R.id.touch_background);
//					backimage
//							.setImageResource(R.drawable.ic_stampmark_white_big_unscaled);
//				}
//
//				return true;
//			}
//		});
		/*
		 * b.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { showDialog(DIALOG_CUSTOM_ID);
		 * Log.i("test", count + ""); count++;
		 * 
		 * StampManager stampmanager = new StampManager();
		 * 
		 * stampmanager.RefreshStamp(getApplicationContext());
		 * stampmanager.RefreshFranchise_used(getApplicationContext());
		 * stampmanager.RefreshFranchise_all(getApplicationContext());
		 * stampmanager.RefreshCompany_all(getApplicationContext()); ImageView
		 * backimage=(ImageView)findViewById(R.id.touch_background);
		 * backimage.setImageResource(R.drawable.ic_stampmark_red_big_unscaled);
		 * 
		 * 
		 * } });
		 */

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tb = (ToggleButton) findViewById(R.id.toggleButton1);
		tb.setChecked(false);
		MultitouchView.stamp_flag = 1;
		
		
		super.onResume();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_CUSTOM_ID:
			dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.tab1_coupon_dialog);

			switch (count) {

			case 0:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 1:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 2:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 3:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 4:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 5:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 6:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 7:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 8:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			case 9:
				i1 = (ImageView) dialog.findViewById(coupons[count]);
				i1.setImageResource(R.drawable.appicon);
				break;
			}

		}
		return dialog;
	}

	/*protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {

		Animation couponAnim = AnimationUtils.loadAnimation(this,
				R.anim.coupon_anim);
		switch (count) {
		case 0:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);

		case 1:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 2:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 3:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 4:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 5:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 6:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 7:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 8:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;
		case 9:
			i1 = (ImageView) dialog.findViewById(coupons[count]);
			i1.setImageResource(R.drawable.appicon);
			i1.startAnimation(couponAnim);
			break;

		}

	}*/

	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {

			case KeyEvent.KEYCODE_MENU:
				// 단말기의 메뉴버튼
				return false;

			}

		}
		return super.dispatchKeyEvent(event);
	}

}
