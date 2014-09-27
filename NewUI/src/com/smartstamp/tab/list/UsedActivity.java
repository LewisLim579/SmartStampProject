package com.smartstamp.tab.list;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartstamp.R;
import com.smartstamp.tab.press.MultitouchView;

public class UsedActivity extends Activity {

	static final int DIALOG_CUSTOM_ID = 0;
	int count = 0;
	ImageView i1, i2;

	static Integer[] coupons = { R.id.coupon1, R.id.coupon2, R.id.coupon3,
			R.id.coupon4, R.id.coupon5, R.id.coupon6, R.id.coupon7,
			R.id.coupon8, R.id.coupon9, R.id.coupon10 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.touch_used);
		ActionBar actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   쿠폰 사용");
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.TouchView_Used);
		View vw = new MultitouchView(getApplicationContext(), null);
		
		rl.addView(vw);
		
		Intent intent = getIntent();
		String code_C = intent.getStringExtra("code_C");
		String code_F = intent.getStringExtra("code_F");
		
		MultitouchView.code_C = code_C;
		MultitouchView.code_F = code_F;
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MultitouchView.stamp_flag = 0;
		super.onResume();
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
