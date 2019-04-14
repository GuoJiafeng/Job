package com.graduationdesign.zhaopin.activity.zhaopin;



import java.util.ArrayList;

import com.graduationdesign.zhaopin.ApplicationInstance;
import com.graduationdesign.zhaopin.R;
import com.graduationdesign.zhaopin.model.ZhaoPinInfo;
import com.graduationdesign.zhaopin.net.HttpGetClient;
import com.graduationdesign.zhaopin.net.OnCompleteListener;
import com.graduationdesign.zhaopin.parser.XmlParser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * ZhaoPinDetailActivity
 * 招聘详情页面
 */


public class ZhaoPinDetailActivity extends Activity implements OnClickListener 
{
	private static final String TAG = "ZhaoPinDetailActivity";

	
	private ZhaoPinInfo zhaopinInfo;

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context=this;
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhaopin_detail_layout);
		
		//获取intent数据
		Log.i(TAG,"INFO:"+"aaaaaaaaa");
		zhaopinInfo = ((ApplicationInstance)getApplication()).getZhaoPinInfo();
		initView(savedInstanceState);
		
	}

	private void initView(Bundle savedInstanceState) 
	{
		findViewById(R.id.imageViewBack).setOnClickListener(this);
		findViewById(R.id.btn_submit_all).setOnClickListener(this);
		((TextView)findViewById(R.id.titleName)).setText("招聘详情");
		((TextView)findViewById(R.id.company_tile)).setText(zhaopinInfo.getCompany());
		//从网络获取
		
		HttpGetClient.getInstance().getZhaoPinDetailFromNet(
				zhaopinInfo.getZhaoPinID()+"");
		HttpGetClient.getInstance().setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(String result) {
						// 解析登录接口回来数据
						Log.i(TAG,"result:"+result.toString());
						ZhaoPinInfo zhaoPinInfoTemp = XmlParser.getInstance()
								.parserZhaoPinDetail(result);
						zhaopinInfo.setAddress(zhaoPinInfoTemp.getAddress());
						zhaopinInfo.setDaiyu(zhaoPinInfoTemp.getDaiyu());
						zhaopinInfo.setGuimo(zhaoPinInfoTemp.getGuimo());
						zhaopinInfo.setNianxian(zhaoPinInfoTemp.getNianxian());
						zhaopinInfo.setRenshu(zhaoPinInfoTemp.getRenshu());
						zhaopinInfo.setXinshui(zhaoPinInfoTemp.getXinshui());
						zhaopinInfo.setXinzhi(zhaoPinInfoTemp.getXinzhi());
						zhaopinInfo.setXueli(zhaoPinInfoTemp.getXueli());
						zhaopinInfo.setZhize(zhaoPinInfoTemp.getZhize());
						zhaopinInfo.setZige(zhaoPinInfoTemp.getZige());
						
						Message msg = new Message();
						msg.what=0;
						Bundle mBundle = new Bundle();
						msg.setData(mBundle);
						mHandler.sendMessage(msg);
					}
				});
	}
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				
				
			Log.i(TAG,"handleMessage:"+"handleMessage");
				if(msg.what==0)
				{
					//del by lxb
					//	
					((TextView)findViewById(R.id.position)).setText("职位名称:"+zhaopinInfo.getPositionInfo());
					((TextView)findViewById(R.id.zhaopin_count)).setText("招聘人数:"+zhaopinInfo.getRenshu());
					//薪水
					String xinshui="";
					if(zhaopinInfo.getXinshui().equals("0"))
					{
						xinshui="面议";
					}
					else if(zhaopinInfo.getXinshui().equals("1"))
					{
						xinshui="3000-4499";
					}
					else if(zhaopinInfo.getXinshui().equals("2"))
					{
						xinshui="4500-7999";
					}
					else if(zhaopinInfo.getXinshui().equals("3"))
					{
						xinshui="8000-9999";
					}
					else if(zhaopinInfo.getXinshui().equals("4"))
					{
						xinshui="10000以上";
					}
					((TextView)findViewById(R.id.yuexin)).setText("薪水范围:"+xinshui);
					((TextView)findViewById(R.id.date)).setText("发布日期:"+zhaopinInfo.getDate());
					((TextView)findViewById(R.id.loc)).setText("工作地点:"+zhaopinInfo.getPlace());
					//工作年限
					String nianxian="";
					if(zhaopinInfo.getNianxian().equals("0"))
					{
						nianxian="不限";
					}
					else if(zhaopinInfo.getNianxian().equals("1"))
					{
						nianxian="应届毕业生";
					}
					else if(zhaopinInfo.getNianxian().equals("2"))
					{
						nianxian="1年";
					}
					else if(zhaopinInfo.getNianxian().equals("3"))
					{
						nianxian="2年";
					}
					else if(zhaopinInfo.getNianxian().equals("4"))
					{
						nianxian="3-4年";
					}
					else if(zhaopinInfo.getNianxian().equals("5"))
					{
						nianxian="5-7年";
					}
					((TextView)findViewById(R.id.nianxian)).setText("工作年限:"+nianxian);
					
					//学历
					String xueli="";
					if(zhaopinInfo.getXueli().equals("0"))
					{
						xueli="不限";
					}
					else if(zhaopinInfo.getXueli().equals("1"))
					{
						xueli="大专";
					}
					else if(zhaopinInfo.getXueli().equals("2"))
					{
						xueli="本科";
					}
					((TextView)findViewById(R.id.xueli)).setText("学历要求:"+xueli);
					((TextView)findViewById(R.id.zhize)).setText(zhaopinInfo.getZhize());
					((TextView)findViewById(R.id.zige)).setText(zhaopinInfo.getZige());
					((TextView)findViewById(R.id.fuli)).setText(zhaopinInfo.getDaiyu());
					
					//性质
					String xinzhi="";
					if(zhaopinInfo.getXinzhi().equals("1"))
					{
						xinzhi="外资";
					}
					else if(zhaopinInfo.getXinzhi().equals("2"))
					{
						xinzhi="合资";
					}
					else if(zhaopinInfo.getXinzhi().equals("3"))
					{
						xinzhi="国企";
					}
					else if(zhaopinInfo.getXinzhi().equals("4"))
					{
						xinzhi="事业单位";
					}
					else if(zhaopinInfo.getXinzhi().equals("5"))
					{
						xinzhi="民营公司";
					}
					((TextView)findViewById(R.id.xinzhi)).setText(xinzhi);
					
					//规模
					String guimo="";
					if(zhaopinInfo.getGuimo().equals("0"))
					{
						guimo="少于50人";
					}
					else if(zhaopinInfo.getGuimo().equals("1"))
					{
						guimo="50-150人";
					}
					else if(zhaopinInfo.getGuimo().equals("2"))
					{
						guimo="150-500人";
					}
					else if(zhaopinInfo.getGuimo().equals("3"))
					{
						guimo="500人以上";
					}
					((TextView)findViewById(R.id.guimo)).setText(guimo);
					((TextView)findViewById(R.id.address)).setText(zhaopinInfo.getAddress());
				}
				else if(msg.what==1)
				{
					//投递成功
					Toast.makeText(ZhaoPinDetailActivity.this, "投递成功!", Toast.LENGTH_SHORT).show();
				}
				else if(msg.what==2)
				{
					//投递失败
					Toast.makeText(ZhaoPinDetailActivity.this, "投递失败!", Toast.LENGTH_SHORT).show();
				}
			
			};
	};
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		//返回按钮
		case R.id.imageViewBack:
			finish();
			break;
		//返回按钮
			//提交
					case R.id.btn_submit_all:
						
						//创建builder
				        AlertDialog.Builder builder = new AlertDialog.Builder(context);
				        builder.setTitle("提示")    //标题
				            .setCancelable(false)    //不响应back按钮
				            .setMessage("确认投递简历？")    //对话框显示内容
				            //设置按钮
				            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				                @Override
				                public void onClick(DialogInterface dialog, int which) {
				                    
				                	dialog.dismiss();
				                    //投简历
				                	SharedPreferences preferences=getSharedPreferences("zhaopin_userinfo", MODE_PRIVATE);

				            		int userID=preferences.getInt("id", 0);
				                	HttpGetClient.getInstance().getPostResumeFromNet(userID,zhaopinInfo.getZhaoPinID());
				            		HttpGetClient.getInstance().setOnCompleteListener(
				            				new OnCompleteListener() {
				            					@Override
				            					public void onComplete(String result) {
				            						// 解析登录接口回来数据
				            						Log.i(TAG,"result:"+result.toString());
				            						boolean bresult = XmlParser.getInstance()
				    										.parserUpdateResumeDetail(result);
				            						Message msg = new Message();
				            						if(bresult)
				    								{
				    									msg.what=1;
				    								}
				    								else
				    								{
				    									msg.what=2;
				    								}
				            						Bundle mBundle = new Bundle();
				            						msg.setData(mBundle);
				            						mHandler.sendMessage(msg);
				            						
				            					}
				            				});
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
						
						
						
						break;
		}
		
	}
	
	//跳转至此activity
	public static void startActivity(Context context, ZhaoPinInfo info)
	{
		Log.i(TAG,"ZhaoPinInfo:"+111);
		//保存
		((ApplicationInstance)context.getApplicationContext()).setZhaoPinInfo(info);
		Intent intent = new Intent(context, ZhaoPinDetailActivity.class);
		context.startActivity(intent);
	}

	

	

	

}
