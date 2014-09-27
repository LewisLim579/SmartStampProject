package com.smartstamp;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler2 {

	private long backKeyPressedTime = 0;
	private Toast toast;

	private Activity activity;

	public BackPressCloseHandler2(Activity context) {
		this.activity = context;
	}

	public void onBackPressed() {
		if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
			backKeyPressedTime = System.currentTimeMillis();
			showGuide();
			return;
		}
		if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
			finish2();
			toast.cancel();
		}
	}


	private void showGuide() {
		toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
				Toast.LENGTH_SHORT);
		toast.show();
	}
	
	
	public void finish2() {
		activity.moveTaskToBack(true);
		activity.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}