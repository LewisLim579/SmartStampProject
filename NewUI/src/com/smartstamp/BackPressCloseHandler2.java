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
		toast = Toast.makeText(activity, "\'�ڷ�\'��ư�� �ѹ� �� �����ø� ����˴ϴ�.",
				Toast.LENGTH_SHORT);
		toast.show();
	}
	
	
	public void finish2() {
		activity.moveTaskToBack(true);
		activity.finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}