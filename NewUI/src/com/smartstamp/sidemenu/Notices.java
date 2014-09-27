package com.smartstamp.sidemenu;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.smartstamp.R;

public class Notices extends Activity {
	ActionBar actionBar;
	WebView mWeb;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notices);

		actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   공지사항");

		
		mWeb = (WebView) this.findViewById(R.id.web1);
		mWeb.setWebViewClient(new WebViewClient());

		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);

		mWeb.loadUrl("http://filey.mooo.com/board/bbs/board.php?bo_table=T3");

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mWeb.canGoBack()) {
			mWeb.goBack();
		} else {
			finish();
		}
	}

}