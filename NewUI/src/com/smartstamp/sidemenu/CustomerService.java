package com.smartstamp.sidemenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.smartstamp.R;

public class CustomerService extends Activity {
	ActionBar actionBar;
	Intent It;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.customerservice);

		actionBar = getActionBar(); // 액션바 객체 얻어서
		actionBar.setTitle("   고객센터");

		actionBar.setIcon(R.drawable.ic_coffee_white_96);


		findViewById(R.id.email_iv_btn1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn4).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn5).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn6).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		findViewById(R.id.email_iv_btn7).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Uri uri=Uri.parse("mailto:smartstamphelp@gmail.com");
		          Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		          startActivity(i);

			}
		});
		

	}
}
