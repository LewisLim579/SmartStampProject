package com.smartstamp.tab.store;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartstamp.R;
import com.smartstamp.database.DatabaseHandler;

public class MapFragment extends Fragment implements OnMyLocationChangeListener {

	public static MapFragment newInstance() {
		MapFragment fragment = new MapFragment();

		return fragment;
	}

	static boolean zoomtomypoint = true;
	GoogleMap mGoogleMap;
	static LatLng loc = new LatLng(37.5817560, 127.0103550); // ��ġ ��ǥ ����, ��Ÿ����

	CameraPosition cp = new CameraPosition.Builder().target((loc)).zoom(14)
			.build();

	MarkerOptions marker = new MarkerOptions().position(loc); // ���۸ʿ� �⺻��Ŀ ǥ��

	Marker m;


	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	ArrayList<String> titles = new ArrayList<String>();

	ArrayList<LatLng> locs = new ArrayList<LatLng>();

	ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

	ArrayList<Marker> markers = new ArrayList<Marker>();

	ArrayList<Integer> logochecks = new ArrayList<Integer>();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	BridgeDataForStamp brdata[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		DatabaseHandler db = new DatabaseHandler(getActivity());// ��񼱾�
		ArrayList<HashMap<String, String>> franchise_all = db
				.getFranchise_all();

		ArrayList<HashMap<String, String>> company_all = db.getCompany_all();

		ArrayList<HashMap<String, String>> franchise = db.getFranchise_used();
		ArrayList<HashMap<String, String>> stampList = db.getStampList();

		int franchiselength = franchise_all.size();
		int franchiselength_used = franchise.size();

		String tmpstamptotal[] = new String[franchiselength];

		for (int i = 0; i < franchiselength; i++)
			tmpstamptotal[i] = "0";

		brdata = new BridgeDataForStamp[franchiselength_used];
		for (int i = 0; i < franchiselength_used; i++) {
			brdata[i] = new BridgeDataForStamp("", "", "");
		}

		for (int i = 0; i < franchiselength_used; i++) {
			brdata[i].company_code = franchise.get(i).get("company_code");
			brdata[i].franchise_code = franchise.get(i).get("franchise_code");
			brdata[i].total_stamp = stampList.get(i).get("SUM(coupon_stamp)");

		}

		mGoogleMap = ((SupportMapFragment) getActivity()
				.getSupportFragmentManager().findFragmentById(R.id.map))
				.getMap();
		// ȭ�鿡 ���۸� ǥ��
		// mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		// // ������ġ�� �̵�

		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp)); // ������ġ��

		// mGoogleMap.addMarker(marker); // ������ġ�� ��Ŀ �߰�

		// ��Ŀ��ġ ����ϴ� �κ�
		for (int i = 0; i < franchiselength; i++) {
			locs.add(new LatLng(Double.parseDouble(franchise_all.get(i).get(
					"franchise_location1")), Double.parseDouble(franchise_all
					.get(i).get("franchise_location2"))));

			titles.add(company_all
					.get(Integer.parseInt(franchise_all.get(i).get(
							"company_code")) - 1).get("company_name")
					+ " " + franchise_all.get(i).get("franchise_name"));


		}
		// ������ �κп� ���Ͽ� ���� �־��ִ� �κ�
		for (int i = 0; i < franchiselength_used; i++) {

			for (int j = 0; j < franchiselength; j++) {

				if ((brdata[i].company_code.equals(franchise_all.get(j).get(
						"company_code")))
						&& (brdata[i].franchise_code.equals(franchise_all
								.get(j).get("franchise_code"))))

					tmpstamptotal[j] = brdata[i].total_stamp;

			}

		}

		// ��Ŀ��ġ�� ��Ŀ�ɼ� �迭�� �ֱ�

		for (int i = 0; i < locs.size(); i++) {

			if (Integer.parseInt(tmpstamptotal[i]) > 10) {

				markerOptions.add(new MarkerOptions()
						.position(locs.get(i))
						.title(titles.get(i))
						.snippet(" ��������  : " + tmpstamptotal[i] + " ��")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.havehave)));

			}

			else if (   (Integer.parseInt(tmpstamptotal[i]) > 0)  && (Integer.parseInt(tmpstamptotal[i]) < 10) ) {

				markerOptions.add(new MarkerOptions()
						.position(locs.get(i))
						.title(titles.get(i))
						.snippet(" ��������  : " + tmpstamptotal[i] + " ��")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.have)));

			}

			else

			{

				markerOptions.add(new MarkerOptions()
						.position(locs.get(i))
						.title(titles.get(i))
						.snippet(" ��������  : " + tmpstamptotal[i] + " ��")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.nohave)));

			}

		}

		// ��Ŀ�迭�� ��Ŀ��ġ�迭�� ���� �ֱ�
		for (int i = 0; i < markerOptions.size(); i++) {

			Marker marker = mGoogleMap.addMarker(markerOptions.get(i));
			markers.add(marker);

		}

		cp = new CameraPosition.Builder().target(loc).zoom(14).build();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

		// Enabling MyLocation Layer of Google Map
		mGoogleMap.setMyLocationEnabled(true);
		// Setting event handler for location change
		mGoogleMap
				.setOnMyLocationChangeListener((OnMyLocationChangeListener) this);

	}

	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
		// ���� ����
		double latitude = location.getLatitude();

		// ���� �浵
		double longitude = location.getLongitude();

		// latLng������ ���� ����, �浵�� ����
		loc = new LatLng(latitude, longitude);

		// ��Ŀ,Ÿ��Ʋ, ������ ǥ��
		if (markers != null) {
			// markers.remove(location).remove(); // ������Ŀ�����
		}
		cp = new CameraPosition.Builder().target(loc).zoom(15).build();

		if (zoomtomypoint) {
			mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp)); // ������ġ��
																				// //
																				// �̵�
			zoomtomypoint = false;
		}

		marker = new MarkerOptions().position(loc).title("������ġ");

		// ������ġ ��Ŀ
		m = mGoogleMap.addMarker(marker);

		// ��Ŀ�� Ÿ��Ʋ,�������� Ŭ������ �� ȣ���
/*		mGoogleMap
				.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker arg0) {

						// TODO Auto-generated method stub

						AlertDialog.Builder alert = new AlertDialog.Builder(
								getActivity());
						alert.setTitle("������ġ�� ������ �Է��Ͻðڽ��ϱ�?");
						alert.setIcon(R.drawable.ic_launcher);
						// positive��ưŬ���� ó���� �̺�Ʈ ��ü ����
						DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// �����ͺ��̽� �Է��۾� ��� ����
								Toast.makeText(getActivity(), "�Է��۾�����",
										Toast.LENGTH_LONG).show();

							}
						};
						alert.setPositiveButton("Ȯ��", positiveClick);
						alert.setNegativeButton("���", null);
						alert.show();
					}

				});*/

		// ��Ŀ�� Ŭ������ �� ȣ���
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {


				return false;

			}

		});

	}

	class BridgeDataForStamp {
		String company_code;
		String franchise_code;
		String total_stamp;

		public BridgeDataForStamp(String mCompany_code, String mFranchise_code,
				String mTotal_stamp) {
			company_code = mCompany_code;
			franchise_code = mFranchise_code;
			total_stamp = mTotal_stamp;
		}
	}

}
