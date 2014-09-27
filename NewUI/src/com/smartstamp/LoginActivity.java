/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.smartstamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;
import com.smartstamp.database.StampManager;
import com.smartstamp.database.UserFunctions;

public class LoginActivity extends Activity {
   Button btnLogin;
   Button btnLinkToRegister;
   EditText inputEmail;
   EditText inputPassword;
   TextView loginErrorMsg;

   // JSON Response node names
   private static String KEY_SUCCESS = "success";
   private static String KEY_ERROR = "error";
   private static String KEY_ERROR_MSG = "error_msg";

   // Login users info
   private static String KEY_UID = "uid";
   private static String KEY_EMAIL = "email";
   private static String KEY_GENDER = "gender";
   private static String KEY_AGE = "age";
   private static String KEY_CREATED_AT = "created_at";

   // Stamp info
   // private static String KEY_EMAIL = "email";
   private static final String KEY_COMPANY_CODE = "company_code";
   private static final String KEY_FRANCHISE_CODE = "franchise_code";
   private static final String KEY_COUPON_STAMP = "coupon_stamp";
   private static final String KEY_FLAG = "flag";
   private static final String KEY_TIME = "time";

   private static final String COUNT = "count";
   private BackPressCloseHandler2 BackPressCloseHandler2;
   private Handler mHandler;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      requestWindowFeature(Window.FEATURE_NO_TITLE);

      setContentView(R.layout.login);
      BackPressCloseHandler2 = new BackPressCloseHandler2(this);
      // Importing all assets like buttons, text fields

      TextView tv_title = (TextView) findViewById(R.id.tv_LoginTitle);
      Typeface face1 = Typeface.createFromAsset(getAssets(),
            "fonts/Roboto-Bold.ttf");

      tv_title.setTypeface(face1);

      inputEmail = (EditText) findViewById(R.id.loginEmail);
      inputPassword = (EditText) findViewById(R.id.loginPassword);
      btnLogin = (Button) findViewById(R.id.btnLogin);
      btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
      loginErrorMsg = (TextView) findViewById(R.id.login_error);

      // Login button Click Event
      btnLogin.setOnClickListener(new View.OnClickListener() {

         public void onClick(View view) {
            new MyTask().execute();

         }
      });

      // Link to Register Screen
      btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

         public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(),
                  RegisterActivity.class);
            startActivity(i);

         }
      });

   }

   public void onBackPressed() {
      BackPressCloseHandler2.onBackPressed();
   }

   public class MyTask extends AsyncTask<String, String, String> {

      private ProgressDialog mProgressDialog;
      final String email = inputEmail.getText().toString();
      final String password = inputPassword.getText().toString();
      final UserFunctions userFunction = new UserFunctions();
      // Log.d("Button", "Login");
      final JSONObject login = userFunction.loginUser(email, password);
      StampManager stampmanager = new StampManager();

      // 로그인을 하기윟여 서버로 이메을과 패스워드를 전송한다.

      @Override
      protected void onPreExecute() {
         mProgressDialog = ProgressDialog.show(LoginActivity.this, "",
               "로딩중입니다. 잠시만 기다려 주세요.", true);
         super.onPreExecute();
      }

      @Override
      protected String doInBackground(String... arg0) {
         // TODO Auto-generated method stub
         // 주 작업
         String status = null;

         try {
            if (login.getString(KEY_SUCCESS) != null) {

               String res = login.getString(KEY_SUCCESS);

               if (Integer.parseInt(res) == 1) {
                  // user successfully logged in
                  // Store user details in SQLite Database
                  DatabaseHandler db = new DatabaseHandler(
                        getApplicationContext());

                  userFunction.logoutUser(getApplicationContext());
                  // 디비를 초기화한다. resettable

                  JSONObject json_user = login.getJSONObject("user");
                  // 서버에서 받은 json 파일에서 유저 테그 읽기.

                  db.putUser(json_user.getString(KEY_EMAIL),
                        login.getString(KEY_UID),
                        json_user.getString(KEY_GENDER),
                        json_user.getString(KEY_AGE),
                        json_user.getString(KEY_CREATED_AT));
                  // 유저 정보 디바이스 디비에 추가하기.

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
                     status = "fail";
                  }
                  // Toast.makeText(getApplication(), "유저 정보 추가 완료",
                  // 0).show();

                  // db.closeDB();
                  // 디비 추가 후 닫기

                  // Toast.makeText(getApplication(), "로그인 완료", 0).show();

               } else {
                  // Error in login
                  // String login_error = login.getString(KEY_ERROR_MSG);
                  status = "fail";

               }

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

            // ############################## 메인 화면 시작
            // 부분######################################
            // Launch Dashboard Screen
            Intent dashboard = new Intent(getApplicationContext(),
                  DashboardActivity.class);

            // Close all views before launching
            // Dashboard
            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(dashboard);

            // Close Login Screen
            LoginActivity.this.finish();
            // ############################## 메인 화면 시작
            // 부분######################################
         } else {
            try {

               String login_error = login.getString(KEY_ERROR_MSG);
               loginErrorMsg.setText(login_error);

            } catch (JSONException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

         }
         mProgressDialog.dismiss();
      }

   }

}