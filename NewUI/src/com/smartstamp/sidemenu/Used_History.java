package com.smartstamp.sidemenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;

public class Used_History extends Activity {
	Integer[] logos = { R.drawable.logo_angel1, R.drawable.logo_caffebene2,
			R.drawable.logo_coffeebean3, R.drawable.logo_ediya4,
			R.drawable.logo_gugunaru5, R.drawable.logo_hollyscoffee6,
			R.drawable.logo_pascucci7, R.drawable.logo_starbucks8,
			R.drawable.logo_tomtom9, R.drawable.logo_twosome10,
			R.drawable.logo_yogerpresso11 };
	ArrayList<HashMap<String, String>> franchise;
	ArrayList<HashMap<String, String>> company;
	ArrayList<HashMap<String, String>> stamplist;
	ArrayList<HashMap<String, String>> used;
	private ListView listview;
	private DataAdapter adapter;
	private LayoutInflater mInflater;
	private ArrayList<CustomData> alist;
	private Context context;
	private TextView title;
	private TextView day;
	private TextView time;
	private ImageView img;
	int uselength;
	LinearLayout ll2;
	RelativeLayout rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo_used_history);
		ActionBar actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   쿠폰 사용내역");
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		ArrayList<HashMap<String, String>> stampused = db.getStamp_used();
		
		
		listview = (ListView) findViewById(R.id.listview_use);
		alist = new ArrayList<CustomData>();
		
		context = this;

		uselength = stampused.size();
		franchise = db.getFranchise_all();
		company = db.getCompany_all();
		

		   rl =(RelativeLayout) findViewById(R.id.example_rl);
		      
		      ll2=(LinearLayout)findViewById(R.id.ll_usedhistory);
		      rl.setVisibility(View.INVISIBLE);
		      ll2.setVisibility(View.INVISIBLE);
		
		
		      Log.d("option",""+stampused.size());
		if (stampused.size() > 0) {
			
		      ll2.setVisibility(View.VISIBLE);
			
			for (int i = 0; i <stampused.size(); i++) {
				String stampused_cf = "" + stampused.get(i).get("company_code")
						+ stampused.get(i).get("franchise_code");
				int stamplist_cf_int = Integer.parseInt(stampused_cf);
			
				for (int j = 0 ; j < franchise.size(); j++) {
					String franchise_cf = ""
							+ franchise.get(j).get("company_code")
							+ franchise.get(j).get("franchise_code");
					int franchise_cf_int = Integer.parseInt(franchise_cf);
					
					if (stamplist_cf_int == franchise_cf_int) {

						String date=stampused.get(i).get("time");
						
						String year=date.substring(2, 4);
						year+=" / ";
						String month=date.substring(5, 7);
						month+=" / ";
						String day_=date.substring(8,10);
						String hour=date.substring(11,13);
						hour+="시 ";
						String min=date.substring(14, 16);
						min+="분";
						
	
						alist.add(new CustomData(this, ""
								+ company.get(
										Integer.parseInt(stampused.get(i).get(
												"company_code")) - 1).get(
										"company_name") + " "
								+ franchise.get(j).get("franchise_name")
							

						, year+""+month+""+day_,hour+""+min,
						logos[Integer.parseInt(franchise.get(j).get("company_code"))-1] ));
						//
					}

				}
			}
			
			adapter = new DataAdapter(this, alist);
			listview.setAdapter(adapter);
			
		} else {		      rl.setVisibility(View.VISIBLE);
		}
		
		

/*		for (int i = 0; i < stampused.size(); i++) {
			Toast.makeText(
					getApplication(),
					stampused.get(i).get("email") + " | "
							+ stampused.get(i).get("company_code") + " | "
							+ stampused.get(i).get("franchise_code") + " | "
							+ stampused.get(i).get("coupon_stamp") + " | "
							+ stampused.get(i).get("flag") + " | "
							+ stampused.get(i).get("time"), 0).show();
		}*/

	}
	
	private class DataAdapter extends ArrayAdapter<CustomData> {
		private LayoutInflater mInflater;

		public DataAdapter(Context context, ArrayList<CustomData> object) {
			super(context, 0, object);

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view = null;
			if (v == null) {
				view = mInflater.inflate(R.layout.myinfo_used_item, null);
			} else {
				view = v;
			}
			final CustomData data = this.getItem(position);
			if (data != null) {
				title = (TextView) view.findViewById(R.id.tv_usedstore);
				day = (TextView) view.findViewById(R.id.tv_usedday);
				time = (TextView) view.findViewById(R.id.tv_usedtime);
				img = (ImageView) view.findViewById(R.id.storeitemimgsmall);
				title.setText(data.getTitle());
				time.setText(data.getTime());
				day.setText(data.getDay());
				img.setImageResource(data.getImg());
			}
			return view;
		}
	}

	private class CustomData {
		private String Title;
		private String Day;
		private String Time;
		private int Img;

		public CustomData(Context context, String mTitle, String mDay, String mTime, int mImg) {
			Title = mTitle;
			Day = mDay;
			Time = mTime;
			Img = mImg;
		}

		public String getTitle() {
			return Title;
		}

		public String getDay() {
			return Day;
		}
		public String getTime() {
			return Time;
		}

		public int getImg() {
			return Img;
		}
	}
}

