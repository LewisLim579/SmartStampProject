/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.smartstamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;
import com.smartstamp.database.StampManager;
import com.smartstamp.database.UserFunctions;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;

	RadioGroup GenderRadio;
	RadioGroup AgeRadio;

	TextView registerErrorMsg;

	Boolean AgeCheck=false;
	Boolean GenderCheck=false;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_EMAIL = "email";
	private static String KEY_GENDER = "gender";
	private static String KEY_AGE = "age";
	private static String KEY_CREATED_AT = "created_at";
	private static String age_tmp = "";
	private static String gender_tmp = "";
	final String GMAIL = "com.google";
	private AccountManager mailManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		// inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);

		GenderRadio = (RadioGroup) findViewById(R.id.registerGenderradio);
		AgeRadio = (RadioGroup) findViewById(R.id.registerAgeradio);

		btnRegister = (Button) findViewById(R.id.btnRegister);

		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		mailManager = AccountManager.get(this);
		for (Account account : mailManager.getAccountsByType(GMAIL)) {
			inputEmail.setText(account.name);
		}

		GenderRadio
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						RadioButton gendercheaked = (RadioButton) findViewById(checkedId);

						gender_tmp = gendercheaked.getTag().toString();
						GenderCheck=true;

					}
				});
		AgeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton agecheaked = (RadioButton) findViewById(checkedId);

				age_tmp = agecheaked.getTag().toString();
				AgeCheck=true;

			}
		});

		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(AgeCheck&&GenderCheck){
					new MyTask().execute();
				}else{
					
					if(!AgeCheck&&GenderCheck){
						Toast.makeText(getApplicationContext(), "연령을 확인해주세요", 0).show();
						
					}else if(AgeCheck&&!GenderCheck){
						Toast.makeText(getApplicationContext(), "성별을 확인해주세요", 0).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "성별과 연령을 확인해주세요", 0).show();
					}
					
				}
				
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}

	public class MyTask extends AsyncTask<String, String, String> {

		private ProgressDialog mProgressDialog;
		String email = inputEmail.getText().toString();
		String password = inputPassword.getText().toString();
		String gender = gender_tmp;
		String age = age_tmp;
		UserFunctions userFunction = new UserFunctions();
		JSONObject json = userFunction.registerUser(email, password, gender,
				age);
		StampManager stampmanager = new StampManager();

		// 로그인을 하기윟여 서버로 이메을과 패스워드를 전송한다.

		@Override
		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(RegisterActivity.this, "",
					"회원가입 중입니다. 잠시만 기다려 주세요.", true);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			// 주 작업
			String status = null;

			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully registred
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(
								getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");


							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.putUser(json_user.getString(KEY_EMAIL),
									json.getString(KEY_UID),
									json_user.getString(KEY_GENDER),
									json_user.getString(KEY_AGE),
									json_user.getString(KEY_CREATED_AT));

							JSONObject log = userFunction.getStamp(email);
							// 로그를 불러오기 위하여 서버로 이메일을 전송한다.

							try {
								if (log.getString(KEY_SUCCESS) != null) {

									stampmanager
											.RefreshStamp(getApplicationContext());
									stampmanager
											.RefreshFranchise_used(getApplicationContext());
									stampmanager
											.RefreshFranchise_all(getApplicationContext());
									stampmanager
											.RefreshCompany_all(getApplicationContext());

									status = "success";
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

							}
						}

					} else {
						// Error in registration
					status = "fail";
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mProgressDialog.dismiss();
			}

			return status;
		}

		@Override
		protected void onPostExecute(String result) {

			// TODO Auto-generated method stub

			// doInBackground에서 작업을 마친 후
			// 프로그래스 다이얼로그 miss
			if (result == "success") {

				// Launch Dashboard Screen
				Intent dashboard = new Intent(getApplicationContext(),
						DashboardActivity.class);
				// Close all views before launching Dashboard
				dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(dashboard);

				// Close Registration Screen
				RegisterActivity.this.finish();
			}

			else {
				try {
					String register_error = json.getString(KEY_ERROR_MSG);
					registerErrorMsg.setText(register_error);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			mProgressDialog.dismiss();
		}

	}
}