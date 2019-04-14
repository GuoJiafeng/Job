package com.graduationdesign.zhaopin.activity.zhaopin;

import java.util.ArrayList;
import java.util.List;


import com.graduationdesign.zhaopin.R;
import com.graduationdesign.zhaopin.adapter.ZhaoPinInfoAdapter;

import com.graduationdesign.zhaopin.model.ZhaoPinInfo;
import com.graduationdesign.zhaopin.net.HttpGetClient;
import com.graduationdesign.zhaopin.net.OnCompleteListener;
import com.graduationdesign.zhaopin.parser.XmlParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * ZhaoPinListActivity
 * 招聘信息列表页面
 * 
 * 
 */
public class ZhaoPinListActivity extends Activity implements OnClickListener,OnItemLongClickListener  
{
	private static final String TAG = "CarNumberListActivity";
	
	private ListView listView;
	private ArrayList<ZhaoPinInfo> zhaoPinInfosList;//简历列表
	private ZhaoPinInfoAdapter adapter;
    Context context;
 // 职位搜索对话框 
 	Dialog dialog;
 	private EditText serch_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		
		initView();
	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhaopin_list_layout);

		((TextView)findViewById(R.id.titleName)).setText("招聘信息");
		Button submitBtn = (Button) findViewById(R.id.okbtn);
		Button interviewBtn = (Button) findViewById(R.id.interview);
		Button  search = (Button) findViewById(R.id.search);
		search.setOnClickListener(this);
		submitBtn.setText("我的简历");
		submitBtn.setOnClickListener(this);
		interviewBtn.setOnClickListener(this);
		
		//serch_text = (EditText)findViewById(R.id.serch_value);
		
		listView = (ListView) findViewById(R.id.car_number_list);
		
		//获取招聘信息列表
		
		zhaoPinInfosList=new ArrayList<ZhaoPinInfo>();
		
		//从网络获取
		Log.i(TAG,"11111111111:");
		HttpGetClient.getInstance().getZhaoPinListFromNet();
		HttpGetClient.getInstance().setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(String result) {
						// 解析登录接口回来数据
						Log.i(TAG,"result:"+result.toString());
						ArrayList<ZhaoPinInfo> zhaoPinInfoListTemp = XmlParser.getInstance()
								.parserZhaoPinInfoList(result);
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
		
		
		
		adapter = new ZhaoPinInfoAdapter(this, zhaoPinInfosList);
		//点击
		listView.setOnItemClickListener(new OnItemClickListener() {  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
                // When clicked, show a toast with the TextView text  
            	final ZhaoPinInfo info=zhaoPinInfosList.get(position);
            	Log.i(TAG,"ZhaoPinInfo:"+info.getCompany());
            	//点击某个信息
            	ZhaoPinDetailActivity.startActivity(context,info);
            }  
        });  
		listView.setOnItemLongClickListener(this);
		listView.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.okbtn:
			//添加
			AddResumeActivity.startActivity(this,1);
			break;
		case R.id.interview:
			//面试邀请
			InterviewListActivity.startActivity(this,1);
			break;
		case R.id.search:
			//职位搜索
			dialog = new Dialog(context, R.style.progress_dialog);
			LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(
					R.layout.activity_serch, null);
			ImageView  search1 = (ImageView) layout.findViewById(R.id.bt_serch);
			search1.setOnClickListener(this);
			serch_text = (EditText) layout
					.findViewById(R.id.serch_value);
			dialog.setContentView(layout);
			dialog.show();
			break;
		case R.id.bt_serch:
			String temp = serch_text.getText().toString();
			if (temp != null && !"".equals(temp)) {
			 	
			}
			else
			{
				temp=""; 	
			}
				//搜索职位
				HttpGetClient.getInstance().getSearchZhaoPinListFromNet(temp);
				HttpGetClient.getInstance().setOnCompleteListener(
						new OnCompleteListener() {
							@Override
							public void onComplete(String result) {
								
								// 解析登录接口回来数据
								Log.i(TAG,"result:"+result.toString());
								ArrayList<ZhaoPinInfo> zhaoPinInfoListTemp = XmlParser.getInstance()
										.parserZhaoPinInfoList(result);
								if(zhaoPinInfoListTemp.size()>0)
								{
									zhaoPinInfosList.clear();
									for(int i=0;i<zhaoPinInfoListTemp.size();i++)
									{
										zhaoPinInfosList.add(zhaoPinInfoListTemp.get(i));
									}
									
									Log.i(TAG,"INFO:"+zhaoPinInfosList.size());
									Message msg = new Message();
									msg.what=1;
									Bundle mBundle = new Bundle();
									msg.setData(mBundle);
									mHandler.sendMessage(msg);
								}
								else
								{
									//没有搜到数据
									Message msg = new Message();
									msg.what=2;
									Bundle mBundle = new Bundle();
									msg.setData(mBundle);
									mHandler.sendMessage(msg);
								}
								
							}
						});
			
			break;	
			
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what==1)
			{
				dialog.dismiss();
			}
			else if(msg.what==2)
			{
				//dialog.dismiss();
				Toast.makeText(context, "没有搜索到对应的职位！", Toast.LENGTH_SHORT)
				.show();
			}
			adapter.notifyDataSetChanged();
		};
	};
	

	
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
	}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		Log.i(TAG,"chang an le :"+position);
		
        final int pos=position;
        final ZhaoPinInfo info=zhaoPinInfosList.get(pos);
		//创建builder
        Builder builder = new Builder(context);
        builder.setTitle("提示")    //标题
            .setCancelable(false)    //不响应back按钮
            .setMessage("确认删除此条信息？")    //对话框显示内容
            //设置按钮
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                	dialog.dismiss();
					
                	zhaoPinInfosList.remove(pos);
            		
            		Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                	if(adapter!=null)
                	{
                		adapter.notifyDataSetChanged();
                	}
            		
                }
            })
          
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {                    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	dialog.dismiss();
                }
            });
        //创建Dialog对象
        builder.create();
        builder.show();


		
		
		
		return true;
	}
	
	
}
