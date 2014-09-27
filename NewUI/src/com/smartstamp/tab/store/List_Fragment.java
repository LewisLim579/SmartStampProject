package com.smartstamp.tab.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;

public class List_Fragment extends Fragment {
	private ListView listview;
	private Context context;
	private DataAdapter adapter;
	private LayoutInflater mInflater;

	private ArrayList<CData> alist;
	private TextView storename;
	private TextView store_address;
	private TextView store_distance;
	private TextView store_number;
	private ImageView img;

	static Integer[] logos = { R.drawable.logo_angel1,
			R.drawable.logo_caffebene2, R.drawable.logo_coffeebean3,
			R.drawable.logo_ediya4, R.drawable.logo_gugunaru5,
			R.drawable.logo_hollyscoffee6, R.drawable.logo_pascucci7,
			R.drawable.logo_starbucks8, R.drawable.logo_tomtom9,
			R.drawable.logo_twosome10, R.drawable.logo_yogerpresso11 };

	private ArrayAdapter<String> arrayAdapter;
	String tmp;
	ArrayList<String> distances = new ArrayList<String>(); // 리스트 액티비티에서활용하기위해

	LocationManager locManager; // 위치 정보 프로바이더
	LocationListener locationListener; // 위치 정보가 업데이트시 동작
	double sortingdistances[];
	double tmpdistance; // 거리값을 임시로 받을 변수
	BridgeData brdata[];
	Location myLocation = new Location("gps");
	ArrayList<Location> forDistanceEvalLocation = new ArrayList<Location>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.liststore);

		locManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);

		myLocation.setLatitude(MapFragment.loc.latitude);
		myLocation.setLongitude(MapFragment.loc.longitude);

		DatabaseHandler db = new DatabaseHandler(getActivity());// 디비선언
		ArrayList<HashMap<String, String>> franchise_all = db
				.getFranchise_all();
		ArrayList<HashMap<String, String>> company_all = db.getCompany_all();

		
		
		
		int franchiselength = franchise_all.size();
		Log.i("tab3", franchiselength + "");

		sortingdistances = new double[franchiselength];

		for (int i = 0; i < franchiselength; i++) {
			sortingdistances[i] = 0;
		}

		Location tmpLocations[] = new Location[franchiselength];
		for (int i = 0; i < franchiselength; i++) {
			tmpLocations[i] = new Location("tmp" + i);
		}
		for (int i = 0; i < franchiselength; i++) {
			tmpLocations[i].setLatitude(Double.parseDouble(franchise_all.get(i)
					.get("franchise_location1")));

			tmpLocations[i].setLongitude(Double.parseDouble(franchise_all
					.get(i).get("franchise_location2")));

			forDistanceEvalLocation.add(tmpLocations[i]);

		}

		brdata = new BridgeData[franchiselength]; //정렬을하기위한 객체배열, 뿌려지는데이터는 여기에 다 들어간다. 
		for (int i = 0; i < franchiselength; i++) {
			brdata[i] = new BridgeData("", "", "", "", 0, 0);
		}

		checkMyLocation(); //내위치를 다시 불러 주기 위함
		
		tmpdistance = 0;
		tmp = "";

		for (int i = 0; i < forDistanceEvalLocation.size(); i++) {
			tmpdistance = Math.round(myLocation
					.distanceTo(forDistanceEvalLocation.get(i)));

			sortingdistances[i] = tmpdistance;

			// 너무 가까운 키로미터로 표시하는게 의미가 없어서 구분
			if (tmpdistance > 1000) {
				tmpdistance /= 1000;
				tmp = Double.toString(tmpdistance);
				distances.add(tmp + " km");

			} else {
				tmp = Double.toString((int) tmpdistance);
				distances.add(tmp + " m");
			}
		}

		
		listview = (ListView) getActivity().findViewById(R.id.storelistview);

		alist = new ArrayList<CData>();
		adapter = new DataAdapter(getActivity(), alist);

		listview.setAdapter(adapter);

		for (int i = 0; i < franchiselength; i++) {

			String printCompany_Store = company_all
					.get(Integer.parseInt(franchise_all.get(i).get(
							"company_code")) - 1).get("company_name")
					+ " " + franchise_all.get(i).get("franchise_name");
			brdata[i].PhoneNumber = franchise_all.get(i).get("phone_num");
			brdata[i].StoreAddress = franchise_all.get(i).get("address");
			brdata[i].StoreDistance = distances.get(i);
			brdata[i].StoreName = printCompany_Store;
			brdata[i].StorelogoImg = logos[Integer.parseInt(franchise_all
					.get(i).get("company_code")) - 1];
			brdata[i].realdistance = sortingdistances[i];
		}

		Arrays.sort(brdata); //거리순으로 소팅

		Log.i("testing", brdata[1].StoreName);

		for (int i = 0; i < franchiselength; i++) {

			adapter.add(new CData(getActivity(), brdata[i].StoreName,
					brdata[i].StoreAddress, brdata[i].PhoneNumber,
					brdata[i].StoreDistance, brdata[i].StorelogoImg));
		}

		
		
		
		
		
		
		
		
		
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CData data = (CData) arg0.getAdapter().getItem(arg2);
				String title = data.getTitle();
/*				Toast.makeText(arg0.getContext(), title, Toast.LENGTH_SHORT)
						.show();*/
			}
		});

	}

	
	
	
	
	
	
	
	public void checkMyLocation() {
		locManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();

		String provider = locManager.getBestProvider(criteria, true);

		locManager.requestLocationUpdates(provider, 10000, 100,
				new MyLocationListener());

		if (provider == null) { // gps off이면 network통해서 받아오도록..
			Toast.makeText(getActivity(), "no GPS Provider", Toast.LENGTH_SHORT)
					.show();
			provider = LocationManager.NETWORK_PROVIDER;
			myLocation = locManager.getLastKnownLocation(provider);
		}

		// myLocation = locManager.getLastKnownLocation(provider);

		if (myLocation == null) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myLocation = locManager.getLastKnownLocation(provider);
		}
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			List_Fragment.this.myLocation = location;
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	private class DataAdapter extends ArrayAdapter<CData> {
		private LayoutInflater mInflater;

		public DataAdapter(Context context, ArrayList<CData> object) {
			super(context, 0, object);

			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view;
			view = null;
			if (v == null) {
				view = mInflater.inflate(R.layout.tab3_liststore_item, null);
			} else {
				view = v;
			}

			final CData data = this.getItem(position);

			if (data != null) {
				storename = (TextView) view.findViewById(R.id.storeitemtv1);
				store_address = (TextView) view.findViewById(R.id.storeitemtv2);
				store_number = (TextView) view.findViewById(R.id.storeitemtv3);
				store_distance = (TextView) view
						.findViewById(R.id.storeitemtvdistance);
				img = (ImageView) view.findViewById(R.id.storeitemimg);

				storename.setText(data.getTitle());
				store_address.setText(data.getAddress());
				store_number.setText(data.getNumber());
				store_distance.setText(data.getDistance());
				img.setImageResource(data.getImg());
			}
			return view;
		}
	}

	private class CData {
		private String StoreName;
		private String StoreAddress;
		private String PhoneNumber;
		private String StoreDistance;
		private int StorelogoImg;

		public CData(Context context, String mTitle, String mAddress,
				String mNumber, String mDistance, int mImg) {
			StoreName = mTitle;
			StoreAddress = mAddress;
			PhoneNumber = mNumber;
			StoreDistance = mDistance;
			StorelogoImg = mImg;
		}

		public String getTitle() {
			return StoreName;
		}

		public String getAddress() {
			return StoreAddress;
		}

		public String getNumber() {
			return PhoneNumber;
		}

		public String getDistance() {
			return StoreDistance;
		}

		public int getImg() {
			return StorelogoImg;
		}
	}

	class BridgeData implements Comparable<BridgeData> {
		String StoreName;
		String StoreAddress;
		String PhoneNumber;
		String StoreDistance;
		int StorelogoImg;
		double realdistance;

		public BridgeData(String mTitle, String mAddress, String mNumber,
				String mDistance, int mImg, double mRealdistance) {
			StoreName = mTitle;
			StoreAddress = mAddress;
			PhoneNumber = mNumber;
			StoreDistance = mDistance;
			StorelogoImg = mImg;
			realdistance = mRealdistance;
		}

		@Override
		public int compareTo(BridgeData data) {
			// TODO Auto-generated method stub
			if (this.realdistance < data.realdistance) {

				return -1;

			} else if (this.realdistance == data.realdistance) {

				return 0;

			} else {

				return 1;

			}

		}
	}

}
