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

public class Favorite_Branch extends Activity {

	DatabaseHandler db;
	ArrayList<HashMap<String, String>> topFranchise;
	ArrayList<HashMap<String, String>> franchise;
	ArrayList<HashMap<String, String>> company;
	LinearLayout ll;
	LinearLayout ll2;
	RelativeLayout rl;
	Integer[] tv = { R.id.tv_favor_1, R.id.tv_favor_2, R.id.tv_favor_3,
			R.id.tv_favor_4, R.id.tv_favor_5 };

	Integer[] tv_company_count = { R.id.tv_rank_count1, R.id.tv_rank_count2,
			R.id.tv_rank_count3, R.id.tv_rank_count4, R.id.tv_rank_count5 };
	Integer[] lv = { R.id.roundlogo1, R.id.roundlogo2, R.id.roundlogo3,
			R.id.roundlogo4, R.id.roundlogo5 };

	static Integer[] logopictures = { R.drawable.background_angel1,
			R.drawable.background_bene2, R.drawable.background_bean3,
			R.drawable.background_ediya4, R.drawable.background_grunaru5,
			R.drawable.background_hollys6, R.drawable.background_pascucci7,
			R.drawable.background_starbucks8, R.drawable.background_tomtom9,
			R.drawable.background_twosome10,
			R.drawable.background_yogerpresso11 };

	int count;
	Integer[] roundlogos = { R.drawable.round_angel1, R.drawable.round_bene2,
			R.drawable.round_bean3, R.drawable.round_ediya4,
			R.drawable.round_grunaru5, R.drawable.round_hollys6,
			R.drawable.round_pascu7, R.drawable.round_star8,
			R.drawable.round_tom9, R.drawable.round_two10,
			R.drawable.round_yoger11 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myinfo_myfavorite_franchise);
		ActionBar actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   즐겨찾는 매장");
		db = new DatabaseHandler(getApplicationContext());
		topFranchise = db.getTopFranchise();
		franchise = db.getFranchise_all();
		company = db.getCompany_all();

		ll = (LinearLayout) findViewById(R.id.ll_top_1);
		
		   rl =(RelativeLayout) findViewById(R.id.example_rl);
		      
		      ll2=(LinearLayout)findViewById(R.id.ll_top_branch);
		      rl.setVisibility(View.INVISIBLE);
		      ll2.setVisibility(View.INVISIBLE);

		Log.i("lewis",topFranchise.size()+"");
		count = 0;

		if (topFranchise.size() > 0) {
		
		      ll2.setVisibility(View.VISIBLE);
			for (int i = topFranchise.size() - 1; i >= topFranchise.size() - 5; i--) {
		
				if (i < 0)
					break;
				String topFranchise_cf = ""
						+ topFranchise.get(i).get("company_code")
						+ topFranchise.get(i).get("franchise_code");
				int topFranchise_cf_int = Integer.parseInt(topFranchise_cf);

				for (int j = franchise.size() - 1; j >= 0; j--) {
					String franchise_cf = ""
							+ franchise.get(j).get("company_code")
							+ franchise.get(j).get("franchise_code");
					int franchise_cf_int = Integer.parseInt(franchise_cf);

					if (topFranchise_cf_int == franchise_cf_int) {

					
						
						
						TextView favor_tv = (TextView) findViewById(tv[count]);
						TextView favor_count = (TextView) findViewById(tv_company_count[count]);
						ImageView roundicon=(ImageView)findViewById(lv[count]);
	
						favor_tv.setText(company.get(
		                         Integer.parseInt(topFranchise.get(i).get(
		                               "company_code"))-1).get("company_name")+" "+franchise.get(j).get("franchise_name"));
						
						if (count==0) {
							ll.setBackgroundResource(logopictures[ Integer.parseInt(topFranchise.get(i).get(
		                               "company_code"))-1]);
						}
						
						favor_count.setText("방문회수 " + topFranchise.get(i)
								 .get("count(company_code)"));
					roundicon.setImageResource(roundlogos[Integer.parseInt(topFranchise.get(i).get(
								 "company_code"))-1]);
						
						
//						
//						 favor_tv.setText(company.get(
//						 Integer.parseInt(topFranchise.get(i).get(
//						 "company_code"))).get("company_name")
//						 + "\n"
//						 + franchise.get(j).get("franchise_name")
//						 + " \n방문회수 : "
//						 + topFranchise.get(i)
//						 .get("count(company_code)"));
					}
				}

				count++;
			}

		} else {
			
		      rl.setVisibility(View.VISIBLE);

		}
	}
}
