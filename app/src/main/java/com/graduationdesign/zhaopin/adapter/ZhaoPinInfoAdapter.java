package com.graduationdesign.zhaopin.adapter;


import java.util.ArrayList;

import com.graduationdesign.zhaopin.R;
import com.graduationdesign.zhaopin.model.ZhaoPinInfo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * ZhaoPinInfoAdapter
 * 招聘信息Adapter
 */
public class ZhaoPinInfoAdapter extends BaseAdapter 
{
	private static final String TAG = "ZhaoPinInfoAdapter";
	
	private Context context;
	private ArrayList<ZhaoPinInfo> zhaoPinInfosList;

	public ZhaoPinInfoAdapter(Context context,
			ArrayList<ZhaoPinInfo> zhaoPinInfosList) {
		super();
		this.context = context;
		this.zhaoPinInfosList = zhaoPinInfosList;
	}

	@Override
	public int getCount() {
		return zhaoPinInfosList.size();
	}

	@Override
	public Object getItem(int position) {
		return zhaoPinInfosList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Holder holder = null;
		final ZhaoPinInfo zhaoPinInfo = zhaoPinInfosList.get(position);
		if (view == null) {
			view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.zhaopin_list_item,
                    parent,
                    false);
			holder = new Holder(view);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		
		holder.positionView.setText(zhaoPinInfo.getPositionInfo());
		holder.companyView.setText(zhaoPinInfo.getCompany());
		holder.date_name.setText("发布时间:"+zhaoPinInfo.getDate());
		holder.palce_name.setText("地点:"+zhaoPinInfo.getPlace());
//		view.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) 
//			{
//				//点击某个车牌
//				CarWeiZhangListActivity.startActivity(context, carInfo);
//			}
//		});
	
		return view;
	}
	
	private class Holder {
		TextView positionView;
		TextView companyView;
		TextView date_name;
		TextView palce_name;
		public Holder(View view) 
		{
			if (view != null) 
			{
				positionView= (TextView) view.findViewById(R.id.position_info);
				companyView= (TextView) view.findViewById(R.id.company_name);
				date_name= (TextView) view.findViewById(R.id.date_name);
				palce_name= (TextView) view.findViewById(R.id.palce_name);
			}
		}
	}

}
