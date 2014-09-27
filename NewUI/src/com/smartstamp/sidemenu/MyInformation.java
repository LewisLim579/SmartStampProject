package com.smartstamp.sidemenu;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.smartstamp.LoginActivity;
import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;
import com.smartstamp.database.StampManager;
import com.smartstamp.database.UserFunctions;

public class MyInformation extends Activity {
	UserFunctions userFunctions = new UserFunctions();
	ActionBar actionBar;
	String gender;
	String firstday;
	TextView useremail;
	TextView usergender_age;
	TextView userfirstday;
	Intent It;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		HashMap<String, String> userinfo = db.getUserDetails();

		setContentView(R.layout.myinformation);

		actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   내 정보");

		/* actionBar.setIcon(R.drawable.ic_coffee_white_96); */
		if (Integer.parseInt(userinfo.get("gender")) == 1)
			gender = "남성";
		else
			gender = "여성";

		firstday = userinfo.get("created_at").substring(0, 10);

		useremail = (TextView) findViewById(R.id.tv_info_userid);
		useremail.setText(userinfo.get("email") + "\n\n동기화");

		usergender_age = (TextView) findViewById(R.id.tv_info_gender_age);

		usergender_age.setText("성별  " + gender + "\n\n연령  "
				+ userinfo.get("age") + "대 ");
		userfirstday = (TextView) findViewById(R.id.tv_info_firstday);
		userfirstday.setText("가입일   " + firstday);

		findViewById(R.id.tv_info_userid).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						new MyTask().execute();

					}
				});
		findViewById(R.id.tv_info_favor_brand).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent intent = new Intent(getApplicationContext(),
								Favorite_Brand.class);
						startActivity(intent);

					}
				});

		findViewById(R.id.tv_info_favor_branch).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent intent = new Intent(getApplicationContext(),
								Favorite_Branch.class);
						startActivity(intent);

					}
				});
		findViewById(R.id.tv_info_save_record).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						It = new Intent(getApplicationContext(),
								com.smartstamp.sidemenu.Save_History.class);
						startActivity(It);

					}
				});
		findViewById(R.id.tv_info_use_record).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						It = new Intent(getApplicationContext(),
								com.smartstamp.sidemenu.Used_History.class);
						startActivity(It);

					}
				});

		findViewById(R.id.logout_tablerow).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						userFunctions.logoutUser(getApplicationContext());
						Intent login = new Intent(getApplicationContext(),
								LoginActivity.class);

						login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						startActivity(login);

					}
				});

	}

	public class MyTask extends AsyncTask<String, String, String> {

		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(MyInformation.this, "",
					"동기화중 입니다.", true);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			// 주 작업
			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
			db.resetFranchise_used();
			

			StampManager stampmanager = new StampManager();
			stampmanager.RefreshStamp(getApplicationContext());
			stampmanager.RefreshFranchise_used(getApplicationContext());
			stampmanager.RefreshFranchise_all(getApplicationContext());
			stampmanager.RefreshCompany_all(getApplicationContext());

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			mProgressDialog.dismiss();
		}

	}

}
