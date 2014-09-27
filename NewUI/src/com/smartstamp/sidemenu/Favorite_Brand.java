package com.smartstamp.sidemenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;

public class Favorite_Brand extends Activity {

	DatabaseHandler db;
	ArrayList<HashMap<String, String>> TopCompany;
	ArrayList<HashMap<String, String>> company;
	LinearLayout ll;
	LinearLayout ll2;
	RelativeLayout rl;
	Integer[] tv = { R.id.tv_favor_11, R.id.tv_favor_22, R.id.tv_favor_33,
			R.id.tv_favor_44, R.id.tv_favor_55 };
	
	
	Integer[] lv={R.id.roundlogo1,R.id.roundlogo2,R.id.roundlogo3,R.id.roundlogo4,R.id.roundlogo5};

	Integer[] tv_company_count = { R.id.tv_rank_count11, R.id.tv_rank_count22,
			R.id.tv_rank_count33, R.id.tv_rank_count44, R.id.tv_rank_count55 };
	
	static Integer[] roundlogos = { R.drawable.round_angel1, R.drawable.round_bene2, R.drawable.round_bean3, R.drawable.round_ediya4,
		 R.drawable.round_grunaru5, R.drawable.round_hollys6, R.drawable.round_pascu7, R.drawable.round_star8, R.drawable.round_tom9, R.drawable.round_two10,  R.drawable.round_yoger11};
	
	static Integer[] logopictures = { R.drawable.background_angel1,
			R.drawable.background_bene2, R.drawable.background_bean3,
			R.drawable.background_ediya4, R.drawable.background_grunaru5,
			R.drawable.background_hollys6, R.drawable.background_pascucci7,
			R.drawable.background_starbucks8, R.drawable.background_tomtom9,
			R.drawable.background_twosome10,
			R.drawable.background_yogerpresso11 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		db = new DatabaseHandler(getApplicationContext());

		TopCompany = db.getTopCompany();
		company = db.getCompany_all();

		setContentView(R.layout.myinfo_myfavorite_company);
		ActionBar actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   즐겨찾는 프렌차이즈");
		ll = (LinearLayout) findViewById(R.id.ll_top);
		   rl =(RelativeLayout) findViewById(R.id.example_rl);
		      rl.setVisibility(View.INVISIBLE);
		      ll2=(LinearLayout)findViewById(R.id.ll_topfran);
		      ll2.setVisibility(View.INVISIBLE);
		int favoriteFranchise_count = 0;
		if (TopCompany.size() > 5) {
			favoriteFranchise_count = 5;

		} else {
			favoriteFranchise_count = TopCompany.size();
		}
		if (favoriteFranchise_count != 0) {
			// log 전체
			for (int i = 0; i < TopCompany.size(); i++) {

				Log.d("fa",
						TopCompany.get(i).get("company_code")
								+ " | "
								+ TopCompany.get(i).get("franchise_code")
								+ " | "
								+ company.get(
										Integer.parseInt(TopCompany.get(i).get(
												"company_code")) - 1).get(
										"company_name") + " | "
								+ TopCompany.get(i).get("time") + " | "
								+ TopCompany.get(i).get("count(company_code)"));

			}

			// top 5

			for (int i = 0; i < favoriteFranchise_count; i++) {
				
				ll2.setVisibility(View.VISIBLE);
				if (i == 0) {
					ll.setBackgroundResource(logopictures[Integer
							.parseInt(TopCompany.get(i).get("company_code")) - 1]);
				}

				TextView favor_tv = (TextView) findViewById(tv[i]);
				TextView favor_count=(TextView)findViewById(tv_company_count[i]);
				ImageView roundicon=(ImageView)findViewById(lv[i]);
				
				favor_tv.setText(company
						.get(Integer.parseInt(TopCompany.get(i).get(
								"company_code")) - 1).get("company_name")
						);
				favor_count.setText("방문회수  "
						+ TopCompany.get(i).get("count(company_code)"));

				roundicon.setImageResource(roundlogos[Integer
							.parseInt(TopCompany.get(i).get("company_code"))-1]);
				
				Log.i("lewis",TopCompany.get(i).get("company_code"));

/*				Log.d("fa",
						TopCompany.get(i).get("company_code")
								+ " | "
								+ TopCompany.get(i).get("franchise_code")
								+ " | "
								+ company.get(
										Integer.parseInt(TopCompany.get(i).get(
												"company_code")) - 1).get(
										"company_name") + " | "
								+ TopCompany.get(i).get("time") + " | "
								+ TopCompany.get(i).get("count(company_code)"));*/

			}

		}

		else {
			      rl.setVisibility(View.VISIBLE);

		}
	}
}
