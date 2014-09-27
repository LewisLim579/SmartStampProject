package com.smartstamp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.smartstamp.R;

public class IntroThin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.intro);

		registerGcm();
		
		 TextView tv_smart=(TextView)findViewById(R.id.tv_intro_smart);
		 TextView tv_stamp=(TextView)findViewById(R.id.tv_intro_stamp);
         Typeface face1=Typeface.createFromAsset(getAssets(),
                                                             "fonts/Roboto-Light.ttf");
         Typeface face2=Typeface.createFromAsset(getAssets(),
                 "fonts/Roboto-Bold.ttf");
         tv_smart.setTypeface(face1);
         tv_stamp.setTypeface(face2);

		

		if (!isNetworkConnected(this)) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("네트워크 연결 오류")
					.setMessage("네트워크 연결 상태 확인 후 다시 시도해 주세요.")

					.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		} else {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					Intent intent = new Intent(IntroThin.this,
							DashboardActivity.class);
					startActivity(intent);
					// 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
					finish();
				}
			}, 2000);
		}

	}

	public boolean isNetworkConnected(Context context) {
		boolean isConnected = false;

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mobile == null) {
			if (wifi.isConnected()) {
				isConnected = true;
			} else {
				isConnected = false;
			}
		} else {

			if (mobile.isConnected() || wifi.isConnected()) {
				isConnected = true;
			} else {
				isConnected = false;
			}
		}
		return isConnected;
	}

	public void registerGcm() {

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);
		Log.i("getidtest", regId);
		if (regId.equals("")) {
			GCMRegistrar.register(this,"120242330393");
		} else {
			Log.i("getidtest", regId);
		}

	}

}
