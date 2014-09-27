package com.smartstamp.database;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class StampManager {

	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR_MSG = "error_msg";

	private static String KEY_EMAIL = "email";

	private static final String KEY_COMPANY_CODE = "company_code";
	private static final String KEY_FRANCHISE_CODE = "franchise_code";
	private static final String KEY_COUPON_STAMP = "coupon_stamp";
	private static final String KEY_FLAG = "flag";
	private static final String KEY_TIME = "time";

	private static final String COUNT = "count";

	// Franchise Table Columns name
	// 같은 이름이라 주석처리, 하지만 사용 한다는 걸 표기하기 위하여 써둠.
	// private static final String KEY_COMPANY_CODE = "company_code";
	// private static final String KEY_FRANCHISE_CODE = "franchise_code";
	private static final String KEY_FRANCHISE_NAME = "franchise_name";
	private static final String KEY_FRANCHISE_LOCATION1 = "franchise_location1";
	private static final String KEY_FRANCHISE_LOCATION2 = "franchise_location2";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_PHONE_NUM = "phone_num";

	// Company Table Columns name
	// 같은 이름이라 주석처리, 하지만 사용 한다는 걸 표기하기 위하여 써둠.
	// private static final String KEY_COMPANY_CODE = "company_code";
	private static final String KEY_COMPANY_NAME = "company_name";

	public Boolean RefreshStamp(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		HashMap<String, String> user = db.getUserDetails();
		UserFunctions userFunction = new UserFunctions();

		String email = user.get("email");

		JSONObject log = userFunction.getStamp(email);

		try {
			if (log.getString(KEY_SUCCESS) != null) {
				String res2 = log.getString(KEY_SUCCESS);

				if (Integer.parseInt(res2) == 1) {

					JSONObject json_stamp;

					int count = Integer.parseInt(log.getString(COUNT));
					// 서버에서 전송받은 레코드 개수를 저장한다.
					userFunction.ResetStamp(context);
					// 스탬프 테이블 초기화

					for (int i = 0; i < count - 1; i++) {
						// 레코드 개수만큼 포문을 돌린다.
						// -1인 이유는 서버에서 받을때 카운트 테그까지 포함하여 개산해서 -1을 한것이다.
						json_stamp = log.getJSONObject("" + i);
						// 레코드 값을 선택하고

						db.putlog(json_stamp.getString(KEY_EMAIL),
								json_stamp.getString(KEY_COMPANY_CODE),
								json_stamp.getString(KEY_FRANCHISE_CODE),
								json_stamp.getString(KEY_COUPON_STAMP),
								json_stamp.getString(KEY_FLAG),
								json_stamp.getString(KEY_TIME));

						// 선택한 레코드의 필드값을 선택하여 디바이스 디비에 전송한다.
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		db.closeDB();
		return true;

	}

	public Boolean RefreshFranchise_used(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		UserFunctions userFunction = new UserFunctions();

		userFunction.ResetFranchise_used(context);
		// 프렌차이저 테이블 초기화

		ArrayList<HashMap<String, String>> stamplist = db.getStampList();

		String company_code;
		String franchise_code;
		int listlength = stamplist.size();
		for (int j = 0; j < listlength; j++) {
			company_code = stamplist.get(j).get("company_code");
			franchise_code = stamplist.get(j).get("franchise_code");

			JSONObject franchise_used = userFunction.getFranchise_used(
					company_code, franchise_code);

			try {
				if (franchise_used.getString(KEY_SUCCESS) != null) {
					String res2 = franchise_used.getString(KEY_SUCCESS);
					if (Integer.parseInt(res2) == 1) {

						JSONObject json_franchise_used;

						json_franchise_used = franchise_used
								.getJSONObject("franchise");

						db.putfranchise_used(json_franchise_used
								.getString(KEY_COMPANY_CODE),
								json_franchise_used
										.getString(KEY_FRANCHISE_CODE),
								json_franchise_used
										.getString(KEY_FRANCHISE_NAME),
								json_franchise_used
										.getString(KEY_FRANCHISE_LOCATION1),
								json_franchise_used
										.getString(KEY_FRANCHISE_LOCATION2),
								json_franchise_used.getString(KEY_ADDRESS),
								json_franchise_used.getString(KEY_PHONE_NUM));

						// 선택한 레코드의 필드값을 선택하여 디바이스 디비에 전송한다.

						// 디비 추가 후 닫기
					} else {
						// Error in login
						String franchise_error = franchise_used
								.getString(KEY_ERROR_MSG);
						// Toast.makeText(context, franchise_error, 0).show();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.closeDB();
		return true;

	}

	public Boolean RefreshCompany_all(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		UserFunctions userFunction = new UserFunctions();

		JSONObject company_all = userFunction.getCompany_all();

		try {
			if (company_all.getString(KEY_SUCCESS) != null) {
				String res2 = company_all.getString(KEY_SUCCESS);

				if (Integer.parseInt(res2) == 1) {

					JSONObject json_company_all;

					int count = Integer.parseInt(company_all.getString(COUNT));
					// 서버에서 전송받은 레코드 개수를 저장한다.
					userFunction.ResetCompany_all(context);
					// 스탬프 테이블 초기화

					for (int i = 0; i < count - 1; i++) {
						// 레코드 개수만큼 포문을 돌린다.
						// -1인 이유는 서버에서 받을때 카운트 테그까지 포함하여 개산해서 -1을 한것이다.
						json_company_all = company_all.getJSONObject("" + i);
						// 레코드 값을 선택하고

						db.putcompany_all(
								json_company_all.getString(KEY_COMPANY_CODE),
								json_company_all.getString(KEY_COMPANY_NAME));

						// 선택한 레코드의 필드값을 선택하여 디바이스 디비에 전송한다.
					}

					db.closeDB();
					// 디비 추가 후 닫기
				} else {
					// Error in login
					String stmap_error = company_all.getString(KEY_ERROR_MSG);
					// Toast.makeText(context, stmap_error, 0).show();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	public Boolean RefreshFranchise_all(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		UserFunctions userFunction = new UserFunctions();

		JSONObject franchise_all = userFunction.getFranchise_all();

		try {
			if (franchise_all.getString(KEY_SUCCESS) != null) {
				String res2 = franchise_all.getString(KEY_SUCCESS);

				if (Integer.parseInt(res2) == 1) {

					JSONObject json_franchise_all;

					int count = Integer
							.parseInt(franchise_all.getString(COUNT));
					// 서버에서 전송받은 레코드 개수를 저장한다.
					userFunction.ResetFranchise_all(context);
					// 스탬프 테이블 초기화

					for (int i = 0; i < count - 1; i++) {
						// 레코드 개수만큼 포문을 돌린다.
						// -1인 이유는 서버에서 받을때 카운트 테그까지 포함하여 개산해서 -1을 한것이다.
						json_franchise_all = franchise_all
								.getJSONObject("" + i);
						// 레코드 값을 선택하고

						db.putfranchise_all(json_franchise_all
								.getString(KEY_COMPANY_CODE),
								json_franchise_all
										.getString(KEY_FRANCHISE_CODE),
								json_franchise_all
										.getString(KEY_FRANCHISE_NAME),
								json_franchise_all
										.getString(KEY_FRANCHISE_LOCATION1),
								json_franchise_all
										.getString(KEY_FRANCHISE_LOCATION2),
								json_franchise_all.getString(KEY_ADDRESS),
								json_franchise_all.getString(KEY_PHONE_NUM));

						// 선택한 레코드의 필드값을 선택하여 디바이스 디비에 전송한다.
					}

					db.closeDB();
					// 디비 추가 후 닫기
				} else {
					// Error in login
					String stmap_error = franchise_all.getString(KEY_ERROR_MSG);
					// Toast.makeText(context, stmap_error, 0).show();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return true;

	}

	public Boolean AddStamp(Context context, String stampV1, String stampV2,
			String stampV3, String stampV4) {
		DatabaseHandler db = new DatabaseHandler(context);
		HashMap<String, String> user = db.getUserDetails();

		// 5.30 추가
		ArrayList<HashMap<String, String>> franchise_all;
		ArrayList<HashMap<String, String>> company;
		ArrayList<HashMap<String, String>> stampList;
		ArrayList<HashMap<String, String>> franchise_used;
		String addDeviceCF = "";
		String allDatabaseCF = "";
		String useDatabaseCF = "";
		String cafeName = "";
		int couponCount = 0;
		// 5.30 추가

		final String email = user.get("email");
		// 디바이스의 이메일 값 받기

		final String stamp1 = stampV1;
		final String stamp2 = stampV2;
		final String stamp3 = stampV3;
		final String stamp4 = stampV4;
		// 스탬프 값 입력.

		UserFunctions userFunction = new UserFunctions();

		JSONObject addServer = userFunction.addStamp(email, stamp1, stamp2,
				stamp3, stamp4);

		try {

			if (addServer.getString(KEY_SUCCESS) != null) {

				String res = addServer.getString(KEY_SUCCESS);

				if (Integer.parseInt(res) == 1) {

					JSONObject addDevice;

					addDevice = addServer.getJSONObject("log");

					db.putlog(addDevice.getString(KEY_EMAIL),
							addDevice.getString(KEY_COMPANY_CODE),
							addDevice.getString(KEY_FRANCHISE_CODE),
							addDevice.getString(KEY_COUPON_STAMP),
							addDevice.getString(KEY_FLAG),
							addDevice.getString(KEY_TIME));

					db.putfranchise_used(addDevice.getString(KEY_COMPANY_CODE),
							addDevice.getString(KEY_FRANCHISE_CODE),
							addDevice.getString(KEY_FRANCHISE_NAME),
							addDevice.getString(KEY_FRANCHISE_LOCATION1),
							addDevice.getString(KEY_FRANCHISE_LOCATION2),
							addDevice.getString(KEY_ADDRESS),
							addDevice.getString(KEY_PHONE_NUM));

					// 5.30 추가
					franchise_all = db.getFranchise_all();
					company = db.getCompany_all();
					stampList = db.getStampList();
					franchise_used = db.getFranchise_used();

					addDeviceCF = addDevice.getString(KEY_COMPANY_CODE)
							+ addDevice.getString(KEY_FRANCHISE_CODE);
					for (int i = 0; i < franchise_all.size(); i++) {
						allDatabaseCF = franchise_all.get(i)
								.get("company_code")
								+ franchise_all.get(i).get("franchise_code");
						if (addDeviceCF.equals(allDatabaseCF)) {
							cafeName = company.get(
									Integer.parseInt(franchise_all.get(i).get(
											"company_code"))-1).get(
									"company_name")
									+ " "
									+ franchise_all.get(i)
											.get("franchise_name");

						}
					}
					for (int j = 0; j < franchise_used.size(); j++) {
						useDatabaseCF = franchise_used.get(j).get(
								"company_code")
								+ franchise_used.get(j).get("franchise_code");
						if (addDeviceCF.equals(useDatabaseCF)) {
							couponCount = Integer.parseInt(stampList.get(j)
									.get("SUM(coupon_stamp)"));
							break;
						}
					}

					Toast.makeText(
							context,
							cafeName + "의 쿠폰이 추가되었습니다.\n보유쿠폰수 : " + couponCount,
							Toast.LENGTH_LONG).show();
					Log.d("abc", cafeName + "의 쿠폰이 추가되었습니다.(" + couponCount
							+ ")");
					// 5.30 추가

					RefreshFranchise_used(context);
				} else {
					// Error in login
					String stmap_error = addServer.getString(KEY_ERROR_MSG);
					// Toast.makeText(context, stmap_error, 0).show();
				}
				db.closeDB();
				// 디비 추가 후 닫기

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Boolean UseStamp(Context context, String stampV1, String stampV2,
			String stampV3, String stampV4, String code_C, String code_F) {
		DatabaseHandler db = new DatabaseHandler(context);
		HashMap<String, String> user = db.getUserDetails();

		// 5.30 추가
		ArrayList<HashMap<String, String>> franchise_all;
		ArrayList<HashMap<String, String>> company;
		ArrayList<HashMap<String, String>> stampList;
		ArrayList<HashMap<String, String>> franchise_used;
		String useDeviceCF = "";
		String allDatabaseCF = "";
		String useDatabaseCF = "";
		String cafeName = "";
		int couponCount = 0;
		// 5.30 추가

		final String email = user.get("email");
		// 디바이스의 이메일 값 받기

		final String stamp1 = stampV1;
		final String stamp2 = stampV2;
		final String stamp3 = stampV3;
		final String stamp4 = stampV4;
		// 스탬프 값 입력.
		
		final String code_c = code_C;
		final String code_f = code_F;
		

		UserFunctions userFunction = new UserFunctions();

		JSONObject useServer = userFunction.useStamp(email, stamp1, stamp2,
				stamp3, stamp4, code_C, code_F);

		try {

			if (useServer.getString(KEY_SUCCESS) != null) {

				String res = useServer.getString(KEY_SUCCESS);

				if (Integer.parseInt(res) == 1) {

					JSONObject useDevice;

					useDevice = useServer.getJSONObject("log");

					db.putlog(useDevice.getString(KEY_EMAIL),
							useDevice.getString(KEY_COMPANY_CODE),
							useDevice.getString(KEY_FRANCHISE_CODE),
							useDevice.getString(KEY_COUPON_STAMP),
							useDevice.getString(KEY_FLAG),
							useDevice.getString(KEY_TIME));

					// 5.30 추가
					franchise_all = db.getFranchise_all();
					company = db.getCompany_all();
					stampList = db.getStampList();
					franchise_used = db.getFranchise_used();

					useDeviceCF = useDevice.getString(KEY_COMPANY_CODE)
							+ useDevice.getString(KEY_FRANCHISE_CODE);
					for (int i = 0; i < franchise_all.size(); i++) {
						allDatabaseCF = franchise_all.get(i)
								.get("company_code")
								+ franchise_all.get(i).get("franchise_code");
						if (useDeviceCF.equals(allDatabaseCF)) {
							cafeName = company.get(
									Integer.parseInt(franchise_all.get(i).get(
											"company_code"))-1).get(
									"company_name")
									+ " "
									+ franchise_all.get(i)
											.get("franchise_name");

						}
					}
					for (int j = 0; j < franchise_used.size(); j++) {
						useDatabaseCF = franchise_used.get(j).get(
								"company_code")
								+ franchise_used.get(j).get("franchise_code");
						if (useDeviceCF.equals(useDatabaseCF)) {
							couponCount = Integer.parseInt(stampList.get(j)
									.get("SUM(coupon_stamp)"));
							break;
						}
					}

					Toast.makeText(
							context,
							cafeName + "의 쿠폰을 사용하였습니다.\n보유쿠폰수 : " + couponCount,
							Toast.LENGTH_LONG).show();
					// 5.30 추가

					// Toast.makeText(context, "스탬프 사용 성공!", 0).show();

				} else {
					// Error in login
					String stmap_error = useServer.getString(KEY_ERROR_MSG);
					 Toast.makeText(context, stmap_error, 0).show();
				}
				db.closeDB();
				// 디비 추가 후 닫기

			}else{
				String stmap_error = useServer.getString(KEY_ERROR_MSG);
				Toast.makeText(context, stmap_error, 0).show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Boolean CancelStamp(Context context, String stampV1, String stampV2,
			String stampV3, String stampV4) {
		DatabaseHandler db = new DatabaseHandler(context);
		HashMap<String, String> user = db.getUserDetails();

		// 5.30 추가
		ArrayList<HashMap<String, String>> franchise_all;
		ArrayList<HashMap<String, String>> company;
		ArrayList<HashMap<String, String>> stampList;
		ArrayList<HashMap<String, String>> franchise_used;
		String cancelDeviceCF = "";
		String allDatabaseCF = "";
		String useDatabaseCF = "";
		String cafeName = "";
		int couponCount = 0;
		// 5.30 추가

		final String useremail = user.get("email");
		// 디바이스의 이메일 값 받기

		final String stamp1 = stampV1;
		final String stamp2 = stampV2;
		final String stamp3 = stampV3;
		final String stamp4 = stampV4;
		// 스탬프 값 입력.

		UserFunctions userFunction = new UserFunctions();

		JSONObject cancelServer = userFunction.cancelStamp(useremail, stamp1,
				stamp2, stamp3, stamp4);

		try {

			if (cancelServer.getString(KEY_SUCCESS) != null) {

				String res = cancelServer.getString(KEY_SUCCESS);

				if (Integer.parseInt(res) == 1) {

					JSONObject cancelDevice;
					cancelDevice = cancelServer.getJSONObject("log");

					db.putlog(cancelDevice.getString(KEY_EMAIL),
							cancelDevice.getString(KEY_COMPANY_CODE),
							cancelDevice.getString(KEY_FRANCHISE_CODE),
							cancelDevice.getString(KEY_COUPON_STAMP),
							cancelDevice.getString(KEY_FLAG),
							cancelDevice.getString(KEY_TIME));

					// 5.30 추가
					franchise_all = db.getFranchise_all();
					company = db.getCompany_all();
					stampList = db.getStampList();
					franchise_used = db.getFranchise_used();

					cancelDeviceCF = cancelDevice.getString(KEY_COMPANY_CODE)
							+ cancelDevice.getString(KEY_FRANCHISE_CODE);
					for (int i = 0; i < franchise_all.size(); i++) {
						allDatabaseCF = franchise_all.get(i)
								.get("company_code")
								+ franchise_all.get(i).get("franchise_code");
						if (cancelDeviceCF.equals(allDatabaseCF)) {
							cafeName = company.get(
									Integer.parseInt(franchise_all.get(i).get(
											"company_code"))-1).get(
									"company_name")
									+ " "
									+ franchise_all.get(i)
											.get("franchise_name");

						}
					}
					for (int j = 0; j < franchise_used.size(); j++) {
						useDatabaseCF = franchise_used.get(j).get(
								"company_code")
								+ franchise_used.get(j).get("franchise_code");
						if (cancelDeviceCF.equals(useDatabaseCF)) {
							couponCount = Integer.parseInt(stampList.get(j)
									.get("SUM(coupon_stamp)"));
							break;
						}
					}

					Toast.makeText(
							context,
							cafeName + "의 쿠폰을 취소하였습니다.\n보유쿠폰수 : " + couponCount,
							Toast.LENGTH_LONG).show();
					// 5.30 추가


				} else {
					// Error in login
					String stmap_error = cancelServer.getString(KEY_ERROR_MSG);
					// Toast.makeText(context, stmap_error, 0).show();
				}
				db.closeDB();
				// 디비 추가 후 닫기

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// public Boolean CancelStamp(Context context, String company_code,
	// String franchise_code) {
	// DatabaseHandler db = new DatabaseHandler(context);
	// HashMap<String, String> user = db.getUserDetails();
	//
	// final String useremail = user.get("email");
	// // 디바이스의 이메일 값 받기
	//
	//
	// final String use_compnay_code = company_code;
	// final String use_frnachise_code = franchise_code;
	// // 사용할 컴퍼니 코트와 프렌차이저 코드 받기
	//
	// UserFunctions userFunction = new UserFunctions();
	//
	// JSONObject cancelServer = userFunction.cancelStamp(useremail,
	// use_compnay_code, use_frnachise_code);
	//
	//
	// try {
	//
	// if (cancelServer.getString(KEY_SUCCESS) != null) {
	//
	// String res = cancelServer.getString(KEY_SUCCESS);
	//
	// if (Integer.parseInt(res) == 1) {
	//
	// JSONObject cancelDevice;
	// cancelDevice = cancelServer.getJSONObject("log");
	//
	// db.putlog(cancelDevice.getString(KEY_EMAIL),
	// cancelDevice.getString(KEY_COMPANY_CODE),
	// cancelDevice.getString(KEY_FRANCHISE_CODE),
	// cancelDevice.getString(KEY_COUPON_STAMP),
	// cancelDevice.getString(KEY_FLAG),
	// cancelDevice.getString(KEY_TIME));
	//
	// Toast.makeText(context,
	// use_compnay_code+"프렌차이저"+use_frnachise_code+"매장 스템프를 취소하셨습니다!",
	// 0).show();
	//
	// } else {
	// // Error in login
	// String stmap_error = cancelServer.getString(KEY_ERROR_MSG);
	// Toast.makeText(context, stmap_error, 0).show();
	// }
	// db.closeDB();
	// // 디비 추가 후 닫기
	//
	// }
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return true;
	// }

}
