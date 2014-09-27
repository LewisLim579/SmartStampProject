/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.smartstamp;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.navdrawer.SimpleSideDrawer;
import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;
import com.smartstamp.database.UserFunctions;
import com.smartstamp.tab.list.ListActivity;
import com.smartstamp.tab.press.TouchActivity;
import com.smartstamp.tab.store.MapFragmentActivity_Manager;

public class DashboardActivity extends TabActivity {
	UserFunctions userFunctions;
	Button btnLogout;
	static TabHost tabHost;
	Intent It;
	static ImageView iv;
	ActionBar actionBar;
	private SimpleSideDrawer mNav;
	static int aTabIcon[] = { R.drawable.tab_1, R.drawable.tab_2,
			R.drawable.tab_3 };
	static int aTabOver[] = { R.drawable.logo_ediya4,
			R.drawable.logo_gugunaru5, R.drawable.logo_hollyscoffee6 };
	static int aTabText[] = { R.string.tab1_title, R.string.tab2_title,
			R.string.tab3_title };
	static String aTabSpec[] = { "Tab01", "Tab02", "Tab03" };
	final int DIALOG_CUSTOM_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   Smart Stamp");
		actionBar.setIcon(R.drawable.ic_coffee_white_96);

		super.onCreate(savedInstanceState);
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		HashMap<String, String> userinfo = db.getUserDetails();
		mNav = new SimpleSideDrawer(this);

		mNav.setRightBehindContentView(R.layout.right_toggle_slide);

		findViewById(R.id.right_myinfo).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						It = new Intent(getApplicationContext(),
								com.smartstamp.sidemenu.MyInformation.class);
						startActivity(It);

					}
				});
		findViewById(R.id.right_notice).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						It = new Intent(getApplicationContext(),
								com.smartstamp.sidemenu.Notices.class);
						startActivity(It);

					}
				});
		findViewById(R.id.right_customer).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						It = new Intent(getApplicationContext(),
								com.smartstamp.sidemenu.CustomerService.class);
						startActivity(It);
					}
				});

		findViewById(R.id.right_version).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						showDialog(DIALOG_CUSTOM_ID);

					}
				});

		findViewById(R.id.right_finish).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						finish();

					}
				});

		/**
		 * Dashboard Screen for the application
		 * */
		// Check login status in database
		userFunctions = new UserFunctions();
		if (userFunctions.isUserLoggedIn(getApplicationContext())) {

			setContentView(R.layout.main);
			tabHost = getTabHost();

			for (int i = 0; i < 3; i++) {
				switch (i) {
				case 0:
					It = new Intent(this, TouchActivity.class);

					break;
				case 1:
					It = new Intent(this, ListActivity.class);

					break;
				case 2:
					It = new Intent(this, MapFragmentActivity_Manager.class);

					break;

				}

				ImageView tabWidget_iv = new ImageView(this);
				tabWidget_iv.setImageResource(aTabIcon[i]);
				// tabHost 생성
				tabHost.addTab(tabHost.newTabSpec(aTabSpec[i])
						.setIndicator(tabWidget_iv)

						.setContent(It));

				tabHost.getTabWidget().getChildAt(i)
						.setBackgroundColor(Color.WHITE);
			}
			// debug Tab
			tabHost.setCurrentTab(0);

			AnimatedTabHostListener tabHostListener = new AnimatedTabHostListener(
					tabHost);
			tabHost.setOnTabChangedListener(tabHostListener);

		} else {
			// user is not logged in show login screen
			Intent login = new Intent(getApplicationContext(),
					LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			// Closing dashboard screen
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = null;

		switch (item.getItemId()) {

		case R.id.item1:

			mNav.toggleRightDrawer();
			break;

		default:
			return false;
		}

		return true;
	}

	protected Dialog onCreateDialog(int id) {

		Dialog dialog = null;

		switch (id) {

		case DIALOG_CUSTOM_ID:

			dialog = new Dialog(this);

			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_developers);


			TextView text = (TextView) dialog.findViewById(R.id.developer_text);

			text.setText("개발\n서인성\n임현우\n장환\n홍영수\n\n디자이너\n배셀라");

			break;

		}

		return dialog;

	}

	public class AnimatedTabHostListener implements OnTabChangeListener {

		private static final int ANIMATION_TIME = 240;
		private TabHost tabHost;
		private View previousView;
		private View currentView;
		private int currentTab;
		Context mContext;

		/**
		 * Constructor that takes the TabHost as a parameter and sets
		 * previousView to the currentView at instantiation
		 * 
		 * @param tabHost
		 */
		public AnimatedTabHostListener(TabHost tabHost) {
			this.tabHost = tabHost;
			this.previousView = tabHost.getCurrentView();
		}

		/**
		 * When tabs change we fetch the current view that we are animating to
		 * and animate it and the previous view in the appropriate directions.
		 */
		@Override
		public void onTabChanged(String tabId) {

			currentView = tabHost.getCurrentView();
			if (tabHost.getCurrentTab() > currentTab) {
				previousView.setAnimation(outToLeftAnimation());
				currentView.setAnimation(inFromRightAnimation());
			} else {
				previousView.setAnimation(outToRightAnimation());
				currentView.setAnimation(inFromLeftAnimation());
			}
			previousView = currentView;
			currentTab = tabHost.getCurrentTab();

			if (tabId == "Tab01")
				actionBar.setTitle("   Smart Stamp");
			else if (tabId == "Tab02")
				actionBar.setTitle("   내 쿠폰");
			else if (tabId == "Tab03")
				actionBar.setTitle("   제휴 매장");

		}

		/**
		 * Custom animation that animates in from right
		 * 
		 * @return Animation the Animation object
		 */
		private Animation inFromRightAnimation() {
			Animation inFromRight = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			return setProperties(inFromRight);
		}

		/**
		 * Custom animation that animates out to the right
		 * 
		 * @return Animation the Animation object
		 */
		private Animation outToRightAnimation() {
			Animation outToRight = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			return setProperties(outToRight);
		}

		/**
		 * Custom animation that animates in from left
		 * 
		 * @return Animation the Animation object
		 */
		private Animation inFromLeftAnimation() {
			Animation inFromLeft = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, -1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			return setProperties(inFromLeft);
		}

		/**
		 * Custom animation that animates out to the left
		 * 
		 * @return Animation the Animation object
		 */
		private Animation outToLeftAnimation() {
			Animation outtoLeft = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, -1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			return setProperties(outtoLeft);
		}

		/**
		 * Helper method that sets some common properties
		 * 
		 * @param animation
		 *            the animation to give common properties
		 * @return the animation with common properties
		 */
		private Animation setProperties(Animation animation) {
			animation.setDuration(ANIMATION_TIME);
			animation.setInterpolator(new AccelerateInterpolator());
			return animation;
		}
	}

}