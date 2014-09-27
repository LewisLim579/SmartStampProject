package com.smartstamp.tab.list;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.smartstamp.BackPressCloseHandler;
import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;
import com.smartstamp.database.StampManager;

public class ListActivity extends Activity {
	private BackPressCloseHandler backPressCloseHandler;

	ArrayList<HashMap<String, String>> franchise;
	ArrayList<HashMap<String, String>> company;
	ArrayList<HashMap<String, String>> stampList;
	RelativeLayout rl;
	DatabaseHandler db;
	static Integer[] logopictures = { R.drawable.background_angel1,
			R.drawable.background_bene2, R.drawable.background_bean3,
			R.drawable.background_ediya4, R.drawable.background_grunaru5,
			R.drawable.background_hollys6, R.drawable.background_pascucci7,
			R.drawable.background_starbucks8, R.drawable.background_tomtom9,
			R.drawable.background_twosome10,
			R.drawable.background_yogerpresso11 };

	static Integer[] ssCoupons = { R.id.sscoupon_1, R.id.sscoupon_2,
			R.id.sscoupon_3, R.id.sscoupon_4, R.id.sscoupon_5 };

	static Integer[] coupons = { R.id.coupon_1, R.id.coupon_2, R.id.coupon_3,
			R.id.coupon_4, R.id.coupon_5, R.id.coupon_6, R.id.coupon_7,
			R.id.coupon_8, R.id.coupon_9, R.id.coupon_10 };

	static Integer[] couponCounts = {

	0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	
	
	static final int DIALOG_CUSTOM_ID = 0;
	static int selectedImageId = 0;
	String code_C="";
	String code_F="";
	
	ListView listView;
	ItemAdapter adapter;
	ArrayList<ItemRow> itemData;
	int franchiselength;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_list_view_recreated);

		backPressCloseHandler = new BackPressCloseHandler(this);

	}

	@Override
	protected void onResume() {
		listView = (ListView) findViewById(R.id.example_swipe_lv_list);
	      listView.setVisibility(View.INVISIBLE);
	      
	      rl =(RelativeLayout) findViewById(R.id.example_rl);
	      rl.setVisibility(View.INVISIBLE);
	      
	      
	      
	      
	      itemData = new ArrayList<ItemRow>();

	   /*   StampManager stampManager = new StampManager();
	      stampManager.RefreshFranchise_used(getApplicationContext());*/
	      
	      db = new DatabaseHandler(getApplicationContext());

	      franchise = db.getFranchise_used();
	      company = db.getCompany_all();
	      stampList = db.getStampList();

	      franchiselength = franchise.size();

	      int coupon_confine=0;
	      if (franchiselength > 0) {

	         
	         listView.setVisibility(View.VISIBLE);
	         
	         for (int i = franchiselength - 1; i >= 0; i--) {

	        	 coupon_confine = Integer.parseInt(stampList.get(i).get(
	                        "SUM(coupon_stamp)"));
	        	 
	        	 if(coupon_confine<0){
	        		 coupon_confine= 0;
	        		 
	        	 }
	        	 
	            itemData.add(new ItemRow(company
	                  .get(Integer.parseInt(franchise.get(i).get(
	                        "company_code")) - 1).get("company_name"),
	                  franchise.get(i).get("franchise_name"),
	                  logopictures[Integer.parseInt(franchise.get(i).get(
	                        "company_code")) - 1], Integer
	                        .parseInt(stampList.get(i).get(
	                              "SUM(coupon_stamp)")),
	                  couponCounts[coupon_confine % 10]));
	            
	           
	            
	         }

	         adapter = new ItemAdapter(this, R.layout.tab2_custom_row, itemData);
	         adapter.notifyDataSetChanged();
	         listView.setAdapter(adapter);
	         listView.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View v,
	                  int position, long id) {

	               selectedImageId = (int) position;
	               Log.d("tab2", selectedImageId + "");
	               
	               
	               code_C = franchise.get((franchiselength-1)-selectedImageId).get("company_code");
		            code_F = franchise.get((franchiselength-1)-selectedImageId).get("franchise_code");
	               
	               showDialog(DIALOG_CUSTOM_ID);

	               // TODO Auto-generated method stub
	            }
	         });


	      }else{
	         rl.setVisibility(View.VISIBLE);
	         
	      }

		super.onResume();
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_CUSTOM_ID:
			dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.tab2_coupon_custom_dialog);

		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, final Dialog dialog, Bundle args) {
		// dialog imageview 초기화
		ImageView iv = null;
		Button useButton = (Button) dialog.findViewById(R.id.use_button);
		useButton.setEnabled(false);
		useButton.setBackgroundColor(Color.parseColor("#808080"));
		for (int i = 0; i < ssCoupons.length; i++) {
			iv = (ImageView) dialog.findViewById(ssCoupons[i]);
			iv.setImageResource(R.drawable.ic_coffee_white_72);
		}
		for (int i = 0; i < coupons.length; i++) {
			iv = (ImageView) dialog.findViewById(coupons[i]);
			iv.setImageResource(R.drawable.dotemptycoupon);
		}
		//

		franchiselength = franchise.size();

		// 쿠폰수
		int coupon_Sum = Integer.parseInt(stampList.get(
				(franchiselength - 1) - selectedImageId).get(
				"SUM(coupon_stamp)"));
		int ssCoupon_Sum = 0;
		if (coupon_Sum >= 10) {
			ssCoupon_Sum = coupon_Sum / 10;
			coupon_Sum %= 10;
			useButton.setEnabled(true);
			useButton.setBackgroundColor(Color.parseColor("#A51E22"));
		}

		if (ssCoupon_Sum != 0) {
			for (int i = 0; i < ssCoupon_Sum; i++) {
				iv = (ImageView) dialog.findViewById(ssCoupons[i]);
				iv.setImageResource(R.drawable.ic_coffee_red_72);
				iv.setVisibility(View.VISIBLE);
			}
		}
		if (coupon_Sum != 0) {
			for (int i = 0; i < coupon_Sum; i++) {
				iv = (ImageView) dialog.findViewById(coupons[i]);
				iv.setImageResource(R.drawable.ic_stampmark_red_72);
				iv.setVisibility(View.VISIBLE);
			}
		}

		
		
		
			
		useButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.cancel();

				// 시간걸어주고 넘기기

				Intent intent = new Intent(ListActivity.this,
						UsedActivity.class);
				
				if(code_C!=""&&code_F!=""){
					intent.putExtra("code_C", code_C);
					intent.putExtra("code_F", code_F);
					
				}
				
				startActivity(intent);

			}
		});

	}

	

	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
