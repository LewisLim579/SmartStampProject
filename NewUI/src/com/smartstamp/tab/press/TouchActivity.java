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
		 * //////////// ����Ʈ �ҷ�����////////////// DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //��� ������ �ϰ�
		 * ArrayList<HashMap<String, String>> stamplist = db.getStampList(); //
		 * ��񿡼� ������ ����Ʈ�� �ҷ���.
		 * 
		 * int listlength = stamplist.size(); //����Ʈ�� ��Ÿ�� ���� ������ ǥ����.
		 * Toast.makeText(getApplication(), ""+listlength, 0).show(); // ����Ʈ��
		 * ��Ÿ�� ���� ����. // ó���� �����Ҷ� �ð������� �����ؼ� 0���� ���� �ֱٿ� �߰��� ������ // �� ���� ���� �����ص�
		 * ���� // ���� �ֱٿ� ������ �������� ���� ���� ���̰� �ϴ°���.
		 * 
		 * for(int i = 0 ; i<listlength;i++){ Toast.makeText(getApplication(),
		 * stamplist
		 * .get(i).get("company_code")+" | "+stamplist.get(i).get("franchise_code"
		 * )+" | "+stamplist.get(i).get("time")+" | "+stamplist.get(i).get(
		 * "count(company_code)"
		 * )+" | "+stamplist.get(i).get("SUM(coupon_stamp)"), 0).show(); }
		 * 
		 * // �̰� ���� �˰��� ???
		 */

		// ////////���������� ���� ����////////////////////
		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //��� ������ �ϰ� ArrayList<HashMap<String, String>> franchise =
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
		 */// Ʈ��ȫ�����Ѱ�

		/*
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //��� ������ �ϰ� ArrayList<HashMap<String, String>> comapny =
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
		 * //��� ������ �ϰ� ArrayList<HashMap<String, String>> franchise_all =
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
		 * // ���� �湮�ϴ� ���������� ��������
		 * 
		 * DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		 * //��� ������ �ϰ� ArrayList<HashMap<String, String>> TopCompany =
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
		 * //���� �湮�ϴ� ���� ���� �������� �Ǿ��־ ���� ������ �Ųٷ� �������� TopFranchise.size���� �����ؼ�
		 * -- �ϱ�.. DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //��� ������ �ϰ�
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
		 * //���� ��볻�� ���� �ֱٿ� ����� ������ ���� ó�� 0��. DatabaseHandler db = new
		 * DatabaseHandler(getApplicationContext()); //��� ������ �ϰ�
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
		 * //��� ������ �ϰ� ArrayList<HashMap<String, String>> stamplist =
		 * db.getStampDetails(); // ��񿡼� ������ ����Ʈ�� �ҷ���.
		 * 
		 * 
		 * //����Ʈ�� ��Ÿ�� ���� ������ ǥ����.
		 * 
		 * 
		 * for(int i = 0 ; i<stamplist.size();i++){
		 * Toast.makeText(getApplication(),
		 * stamplist.get(i).get("company_code")+
		 * " | "+stamplist.get(i).get("franchise_code"
		 * )+" | "+stamplist.get(i).get("time"), 0).show(); }
		 */

		/*
		 * //������ ����ϴ� �κ�. StampManager stampmanager = new StampManager();
		 * stampmanager.UseStamp(getApplicationContext(),company_code,
		 * franchise_code); //���� �̸����� �˾Ƽ� �޾� ���ϱ� ����Ϸ��� ���۴��ڵ�� ���������� �ڵ常 �ܾ�ͼ�
		 * �־��ָ� ��!
		 * 
		 * //������ ����ϴ� �κ� StampManager stampmanager = new StampManager();
		 * stampmanager.CancelStamp(getApplicationContext(),company_code,
		 * franchise_code); //���� �̸����� �˾Ƽ� �޾� ���ϱ� ����Ϸ��� ���۴��ڵ�� ���������� �ڵ常 �ܾ�ͼ�
		 * �־��ָ� ��!
		 * 
		 * 
		 * //�Ѵ� �������� �̷������ ������ //����̽����� ������ email,company_code,franchise_code��
		 * ������ �װ� ������ �α׸� -10, -1 �� �߰��ϴ� ���̴�.
		 */

		/*
		 * double w = getWindowManager().getDefaultDisplay().getWidth(); double
		 * h = getWindowManager().getDefaultDisplay().getHeight(); ����̽��� �ػ�
		 * ���ϱ�.
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
				// �ܸ����� �޴���ư
				return false;

			}

		}
		return super.dispatchKeyEvent(event);
	}

}
