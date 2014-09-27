package com.smartstamp.tab.store;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartstamp.BackPressCloseHandler;
import com.smartstamp.R;

public class MapFragmentActivity_Manager extends FragmentActivity {
	private BackPressCloseHandler backPressCloseHandler;
	int mCurrentFragmentIndex=0;
	Button changeButton;
	ImageButton ibList;
	ImageButton ibMap;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_mab_and_list);
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		chkGpsService();
		final FrameLayout fl = (FrameLayout) findViewById(R.id.googlemap);
		final LinearLayout ll = (LinearLayout) findViewById(R.id.maplist);
		
		
		if(mCurrentFragmentIndex == 0){
			fl.setVisibility(View.VISIBLE);
			ll.setVisibility(View.INVISIBLE);
		}else if(mCurrentFragmentIndex == 1){
			fl.setVisibility(View.INVISIBLE);
			ll.setVisibility(View.VISIBLE);
		}
		//fl.setVisibility(View.INVISIBLE);
		//ll.setVisibility(View.INVISIBLE);
		
		fragmentReplace(mCurrentFragmentIndex);
		
		ibList=(ImageButton)findViewById(R.id.imageButtontolist);
		ibList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ibMap.setImageResource(R.drawable.tab3_mapbtn11);
				ibList.setImageResource(R.drawable.tab3_listbtn22);
				
				// TODO Auto-generated method stub
				if(mCurrentFragmentIndex == 0){
				mCurrentFragmentIndex =1;
				
				fl.setVisibility(View.INVISIBLE);
				ll.setVisibility(View.VISIBLE);
				fragmentReplace(mCurrentFragmentIndex);
				}
			}
	
		});
		ibMap=(ImageButton)findViewById(R.id.imageButtontomap);
		ibMap.setImageResource(R.drawable.tab3_mapbtn22);
		ibMap.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				ibMap.setImageResource(R.drawable.tab3_mapbtn22);
				ibList.setImageResource(R.drawable.tab3_listbtn11);
				// TODO Auto-generated method stub
				if(mCurrentFragmentIndex == 1){
				mCurrentFragmentIndex = 0;
				fl.setVisibility(View.VISIBLE);
				ll.setVisibility(View.INVISIBLE);
				fragmentReplace(mCurrentFragmentIndex);
				}
			}
		});
/*		changeButton = (Button)findViewById(R.id.maplistchangebutton);
		changeButton.setText("리스트");
		changeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurrentFragmentIndex == 0){
					mCurrentFragmentIndex =1;
					
					fl.setVisibility(View.INVISIBLE);
					ll.setVisibility(View.VISIBLE);
					fragmentReplace(mCurrentFragmentIndex);
					changeButton.setText("지도");
					
					
				}else if(mCurrentFragmentIndex == 1){
					mCurrentFragmentIndex = 0;
					fl.setVisibility(View.VISIBLE);
					ll.setVisibility(View.INVISIBLE);
					fragmentReplace(mCurrentFragmentIndex);
					changeButton.setText("리스트");
				}
				
				
			}
		});*/
		
		
		

	}
	
	private boolean chkGpsService() {
	    
	    String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	      
	    Log.d(gps, "aaaa");  
	    
	    if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {
	   
	     // GPS OFF 일때 Dialog 표시 
	     AlertDialog.Builder gsDialog = new AlertDialog.Builder(this); 
	     gsDialog.setTitle("위치 서비스 설정");   
	     gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?"); 
	     gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
	      public void onClick(DialogInterface dialog, int which) { 
	       // GPS설정 화면으로 이동 
	       Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS); 
	       intent.addCategory(Intent.CATEGORY_DEFAULT); 
	       startActivity(intent); 
	      } 
	     })
	     .setNegativeButton("NO", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int which) {
	       return;
	      }
	     }).create().show();
	     return false;
	    
	    } else { 
	     return true; 
	    } 
	   }
	
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}


	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment = null;

		// Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex);

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.content, newFragment);

		// Commit the transaction
		transaction.commit();

	}
	
	
	 private Fragment getFragment(int idx) {
	        Fragment newFragment = null;
	 
	        switch (idx) {
	        case 0:
	            newFragment = new MapFragment();
	            break;
	        case 1:
	        	newFragment = new List_Fragment();
	        	break;
	        default:
	        	//Log.d(TAG, "Unhandle case");
	        	newFragment = null;
	            break;
	        }
	 
	        return newFragment;
	    }
	 
		
	
	
}