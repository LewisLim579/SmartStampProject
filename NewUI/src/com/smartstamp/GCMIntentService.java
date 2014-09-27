package com.smartstamp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.smartstamp.R;
import com.smartstamp.database.UserFunctions;

public class GCMIntentService extends GCMBaseIntentService {

	private static void generateNotification(Context context, String message) {

		int icon = R.drawable.ic_launcher_ver2_48;
		long when = System.currentTimeMillis();

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, IntroThin.class);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, title, message, intent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, notification);

	}

	@Override
	protected void onError(Context arg0, String arg1) {

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
	 String test="";
	String msg = intent.getStringExtra("msg");
	
	   try {
		test = URLDecoder.decode(msg,"euc-kr");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Log.e("getmessage", "getmessage:" + msg);
	generateNotification(context,test);
	 
	}

	@Override
	protected void onRegistered(Context context, String reg_id) {
		Log.e("키를 등록합니다.(GCM INTENTSERVICE)", reg_id);
		UserFunctions userFunctions = new UserFunctions();
		userFunctions.registerGCM(reg_id);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.e("키를 제거합니다.(GCM INTENTSERVICE)", "제거되었습니다.");
	}

}
