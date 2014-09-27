/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.smartstamp.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserFunctions {
	
	private JSONParser jsonParser;

	private static String smartstampURL = "http://filey.mooo.com/copy_android_login_api/android_login_api/";
	
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	private static String getstamp_tag = "getstamp";
	private static String addstamp_tag = "addstamp";
	private static String usestamp_tag = "usestamp";
	private static String cancelstamp_tag = "cancelstamp";
	
	
	private static String getfranchiseused_tag = "getfranchiseused";
	
	private static String getcompanyall_tag = "getcompanyall";
	private static String getfranchiseall_tag = "getfranchiseall";
	
	private static String registergcm_tag = "registergcm";
	
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	 
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Stamp Request
	 * @param email
	 * */
	// 사용자의 스탬프를 찾기위하여 쿼리 전송용 이메일을 넘긴다.
	public JSONObject getStamp(String email){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getstamp_tag));
		//테그를 붙이고
		params.add(new BasicNameValuePair("email", email));
		//이메일을 전송한다.
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	
	public JSONObject addStamp(String email, String stamp1,String stamp2,String stamp3,String stamp4){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", addstamp_tag));
		//테그를 붙이고
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("stamp1", stamp1));
		params.add(new BasicNameValuePair("stamp2", stamp2));
		params.add(new BasicNameValuePair("stamp3", stamp3));
		params.add(new BasicNameValuePair("stamp4", stamp4));
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	public JSONObject useStamp(String email, String stamp1,String stamp2,String stamp3,String stamp4,String code_C, String code_F){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", usestamp_tag));
		//테그를 붙이고
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("stamp1", stamp1));
		params.add(new BasicNameValuePair("stamp2", stamp2));
		params.add(new BasicNameValuePair("stamp3", stamp3));
		params.add(new BasicNameValuePair("stamp4", stamp4));
		params.add(new BasicNameValuePair("code_c", code_C));
		params.add(new BasicNameValuePair("code_f", code_F));
		
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	public JSONObject cancelStamp(String email, String stamp1,String stamp2,String stamp3,String stamp4){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", cancelstamp_tag));
		//테그를 붙이고
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("stamp1", stamp1));
		params.add(new BasicNameValuePair("stamp2", stamp2));
		params.add(new BasicNameValuePair("stamp3", stamp3));
		params.add(new BasicNameValuePair("stamp4", stamp4));
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	
	public JSONObject getFranchise_used(String company_code, String franchise_code){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getfranchiseused_tag));
		//테그를 붙이고
		params.add(new BasicNameValuePair("company_code", company_code));
		params.add(new BasicNameValuePair("franchise_code", franchise_code));
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	public JSONObject getCompany_all(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getcompanyall_tag));
		//테그를 붙이고
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	public JSONObject getFranchise_all(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getfranchiseall_tag));
		//테그를 붙이고
		
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		//유알엘로.
		
		return json;
	}
	
	
	
	
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * @param gender
	 * @param age
	 * */
	public JSONObject registerUser(String email, String password, String gender, String age){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("gender", gender));
		params.add(new BasicNameValuePair("age", age));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		// return json
		return json;
	}
	
	
	//푸쉬서버 아이디 등록 부분
	public JSONObject registerGCM(String regId){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", registergcm_tag));
		params.add(new BasicNameValuePair("regid", regId));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(smartstampURL, params);
		// return json
		return json;
	}
	
	
	
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	public boolean ResetStamp(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetStamp();
		return true;
	}
	
	public boolean ResetFranchise_used(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetFranchise_used();
		return true;
	}
	
	public boolean ResetCompany_all(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetCompany_all();
		return true;
	}
	
	public boolean ResetFranchise_all(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetFranchise_all();
		return true;
	}
	
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}
