package com.tleaf.tiary;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapLongClickListener, OnInfoWindowClickListener
{
	private GoogleMap mMap;
	// private Marker mkr = null;
	private String location;

	private double mLatitude;
	private double mLongitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mLatitude = getIntent().getDoubleExtra("mLatitude", 37.566535);
		mLongitude = getIntent().getDoubleExtra("mLongitude", 126.977969);
		Log.e("onCreate mLatitude, mLongitude: ", mLatitude + "/" + mLongitude);
		location = getIntent().getStringExtra("location");
		Log.e("onCreate location: ", location);

		setUpMapIfNeeded();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		setUpMapIfNeeded();
	}

	//���� �ʱ�ȭ�Ѵ�.
	private void setUpMapIfNeeded() { // first_method_call
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
			if (mMap != null)
				setUpMap();
		} else
			setUpMap();
	}

	//�ŷ���Ұ� �Ѿ� �Դ��� Ȯ���Ѵ�
	//�ŷ���Ұ� �Ѿ���� �ʾ��� ��� ���� ����Ʈ�� �ʱ�ȭ�Ѵ�
	private void setUpMap() { // second_method_call
		Log.e("setUpMap mLatitude, mLongitude: ", mLatitude + "/" + mLongitude);
		LatLng myLocation = new LatLng(mLatitude, mLongitude);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));// ����� ����
		mMap.setMyLocationEnabled(true);
		mMap.setOnMapLongClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		updateMyLocation(); // �̰Կ��ʿ�?

		if (location == null || location.equals("")) {
			Log.i("tag", "no recieve location");
			Toast.makeText(this, "�ŷ� ��� �̵��", 	Toast.LENGTH_SHORT).show();
		} else
			getAddressPoint(location);
	}

	//���� �� ��ġ�� ������ ī�޶� �������ش�
	public void onMyLocationToggled(View view)
	{
		updateMyLocation();
	}

	private void updateMyLocation()
	{
		if (mMap == null)
			return;
		mMap.setMyLocationEnabled(true);
	}

	//��Ŀ���� �����츦 Ŭ�������� ��Ƽ��Ƽ�� ����������
	// click InfoWindow
	@Override
	public void onInfoWindowClick(Marker mkr)
	{
		// TODO Auto-generated method stub
//		Intent intent = new Intent();
//		intent.putExtra("send_address_text", mkr.getTitle());
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		setResult(RESULT_OK, intent);
		finish();
	}

	// longClick and put a mark on map
	@Override
	public void onMapLongClick(LatLng point)
	{
		// TODO Auto-generated method stub
//		Log.d("tag", "map activity longclick");
//		makeMarker(point);
	}

	// receive longClick_point and send addressText
	public String getAddressText(double lat, double lng) {
		Geocoder pt_to_txt = new Geocoder(getApplicationContext(), Locale.getDefault());
		String address_text = "�ּҸ��� �� �� ����";
		try	{
			List<Address> list = pt_to_txt.getFromLocation(lat, lng, 1);
			StringBuilder sb = new StringBuilder();

			if (list != null && list.size() > 0) {
				Address ads = list.get(0);
				sb.append(ads.getAddressLine(0) + " ");
				address_text = sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return address_text;
	}

	//�ŷ����� ������ ã�� ��Ŀ�� ��� �޼��忡�� ������
	// receive addressText and send addressPoint to makeMarker()
	public void getAddressPoint(String location) {
		Log.d("getAddressPoint location: ", location);
		Geocoder txt_to_pt = new Geocoder(getApplicationContext(), Locale.getDefault());
		try {
			List<Address> list = txt_to_pt.getFromLocationName(location, 1);
			Log.d("tag", "getFromLocationName listsize:" + list.size() + ",");
			if (list.size() == 0) {
				Toast.makeText(this, "����� �ŷ����� ã�� �� ����", Toast.LENGTH_SHORT).show();
				return;
			}
			Address ads = list.get(0);
			LatLng point = new LatLng(ads.getLatitude(), ads.getLongitude());
			Log.d("ads.getLatitude, ads.getLongitude", ads.getLatitude() + "/" + ads.getLongitude());
			makeMarker(point);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//��Ŀ�� ��´�
	// make marker and add showInfoWindow at marker
	public void makeMarker(LatLng point)
	{
		mMap.clear();
		Log.d("makeMarker ads.getLatitude, ads.getLongitude:", point.latitude + "/" + point.longitude);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
		String mark_address = getAddressText(point.latitude, point.longitude); // ��ǥ������ �ּҸ� �޾ƿ�
		Log.d("mark_address:", mark_address);
		mMap.addMarker(new MarkerOptions().position(point).title(mark_address)
				.draggable(true)).showInfoWindow(); // ��ǥ���� ��Ŀ�� ��´�
		// //snippet()
	}

}
