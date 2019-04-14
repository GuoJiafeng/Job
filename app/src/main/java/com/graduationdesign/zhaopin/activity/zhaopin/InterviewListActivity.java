package com.graduationdesign.zhaopin.activity.zhaopin;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import com.graduationdesign.zhaopin.ApplicationInstance;
import com.graduationdesign.zhaopin.R;

import com.graduationdesign.zhaopin.adapter.InterviewInfoAdapter;
import com.graduationdesign.zhaopin.adapter.ZhaoPinInfoAdapter;

import com.graduationdesign.zhaopin.model.ZhaoPinInfo;
import com.graduationdesign.zhaopin.net.HttpGetClient;
import com.graduationdesign.zhaopin.net.OnCompleteListener;
import com.graduationdesign.zhaopin.parser.XmlParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * InterviewListActivity
 * 面试列表Activity
 * 
 * 
 */
public class InterviewListActivity extends Activity implements OnClickListener
{
	private static final String TAG = "InterviewListActivity";
	
	int userID;
	private ListView listView;
	private ArrayList<ZhaoPinInfo> zhaoPinInfosList;//面试列表
	private InterviewInfoAdapter adapter;
    Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		SharedPreferences preferences=getSharedPreferences("zhaopin_userinfo", MODE_PRIVATE);
		userID=preferences.getInt("id", 0);
		
		initView();
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.interview_list_layout);

		((TextView)findViewById(R.id.titleName)).setText("面试信息");
		
		findViewById(R.id.imageViewBack).setOnClickListener(this);
		
		listView = (ListView) findViewById(R.id.car_number_list);
		
		//招聘信息列表
		
		zhaoPinInfosList=new ArrayList<ZhaoPinInfo>();
		
		
		//从网络获取
		Log.i(TAG,"11111111111:");
		HttpGetClient.getInstance().getZhaoInterviewFromNet(userID);
		HttpGetClient.getInstance().setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(String result) {
						// 解析登录接口回来数据
						Log.i(TAG,"result:"+result.toString());
						ArrayList<ZhaoPinInfo> zhaoPinInfoListTemp = XmlParser.getInstance()
								.parserInterViewInfoList(result);
						for(int i=0;i<zhaoPinInfoListTemp.size();i++)
						{
							zhaoPinInfosList.add(zhaoPinInfoListTemp.get(i));
						}
						
						Log.i(TAG,"INFO:"+zhaoPinInfosList.size());
						Message msg = new Message();
						Bundle mBundle = new Bundle();
						msg.setData(mBundle);
						mHandler.sendMessage(msg);
						
					}
				});
		
		//carInfosList = new ArrayList<CarNumberInfo>();
		//从数据库读取
		//carInfosList = CarNumberInfo.getAllCarNumberInfoListFromDB(this);
		//Log.i(TAG,"carInfosList:"+carInfosList.toString());
		
		//adapter = new CarNumberListAdapter(this, carInfosList);
		
		
		
		adapter = new InterviewInfoAdapter(this, zhaoPinInfosList);
		//点击
		listView.setOnItemClickListener(new OnItemClickListener() {  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
                // When clicked, show a toast with the TextView text  
            	final ZhaoPinInfo info=zhaoPinInfosList.get(position);
            	Log.i(TAG,"ZhaoPinInfo:"+info.getCompany());
            	//点击某个信息进地图
            	//ZhaoPinDetailActivity.startActivity(context,info);
            	
            	Intent intent;
				try {
					//调用百度地图
					intent = Intent.getIntent("intent://map/geocoder?address="+info.getAddress()+"&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"); 
					startActivity(intent); //启动调用 
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(context, "请安装百度地图APP！", Toast.LENGTH_SHORT)
					.show();
				}
            	
            }  
        });  
		listView.setAdapter(adapter);
	}
	 
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			
			adapter.notifyDataSetChanged();
		};
	};
	

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.imageViewBack:
			finish();
			break;
			
			
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();

	}

	
	//跳转至此activity
	public static void startActivity(Context context,int i)
	{
	       
		Intent intent = new Intent(context, InterviewListActivity.class);
		
		context.startActivity(intent);
	}
	
	
}
