package com.smartstamp.tab.list;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartstamp.R;

public class ItemAdapter extends ArrayAdapter<ItemRow>
{
	private Context mContext;
	private int mLayoutResource;
	private ArrayList<ItemRow> mList;
	
	private LayoutInflater mInflater;
	
	public ItemAdapter(Context context, int rowLayoutResource, ArrayList<ItemRow> objects)
	{
		super(context, rowLayoutResource, objects);
		this.mContext = context;
		this.mLayoutResource = rowLayoutResource;
		this.mList = objects;
		this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public ItemRow getItem(int position)
	{
		return mList.get(position);
	}

	@Override
	public int getPosition(ItemRow item)
	{
		return mList.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		
		if(convertView == null)
		{
			convertView = mInflater.inflate(mLayoutResource, null);
		}
		
		ItemRow data = getItem(position);
		NewsHolder holder = null;
		
		if(data != null)
		{
			holder = new NewsHolder();
			
			holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
			holder.tv_branch = (TextView) convertView.findViewById(R.id.tv_branch);
			holder.front = (RelativeLayout) convertView.findViewById(R.id.front);
			holder.tv_CouponTotal = (TextView) convertView
					.findViewById(R.id.tv_coupontotal);
//			ImageView ivImage = (ImageView)convertView.findViewById(R.id.listlogoid);
//			TextView tvTitle = (TextView)convertView.findViewById(R.id.caffetitleid);
//			TextView tvStore = (TextView)convertView.findViewById(R.id.caffestoreid);
//			
//			ivImage.setImageResource(data.getImage());
//			tvTitle.setText(data.getTitle());
//			tvStore.setText(data.getstorename());
			Log.d("tab2", position+"");
			
			holder.tv_brand.setText(data.getCafeTitle());
			holder.tv_branch.setText(data.getCafeMakerNames());
			holder.front.setBackgroundResource(data.getCafeBackgrounds());
			holder.tv_CouponTotal.setText("ÄíÆù : " + data.getCoupon());

		}

		return convertView;
	}
	static class NewsHolder {

		RelativeLayout front;
		TextView tv_brand;
		TextView tv_branch;
		TextView tv_CouponTotal;


	}
}
