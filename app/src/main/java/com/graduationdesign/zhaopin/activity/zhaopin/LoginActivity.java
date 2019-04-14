package com.graduationdesign.zhaopin.activity.zhaopin;


import com.graduationdesign.zhaopin.R;
import com.graduationdesign.zhaopin.model.Resume;
import com.graduationdesign.zhaopin.net.HttpGetClient;
import com.graduationdesign.zhaopin.net.OnCompleteListener;
import com.graduationdesign.zhaopin.parser.XmlParser;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/*
 * 注册登录页面
 * LoginActivity
 */
public class LoginActivity extends Activity implements OnClickListener{

	private static final String TAG = "LoginActivity";
	EditText username;
	EditText passwd;
	String name;
	String password;
	String registName;
	String registPassword;
	SharedPreferences preferences;
	Editor editor;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}

	//初始化界面,从布局中获得按钮
	private void initView(){
		username=(EditText)findViewById(R.id.username);
		passwd=(EditText)findViewById(R.id.passwd);
		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.regist).setOnClickListener(this);
		
		
		CheckUserData();//从sharedpreferences中取出账号
	}
	
	//检验输入账号密码
	public void EditNamePasswd()
	{
		name=username.getText().toString().trim();
		password=passwd.getText().toString().trim();
		if(!name.equals(""))
		{
			if(!password.equals(""))
			{
				Login(name, password);
			}
			else
			{
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			}
		}else
		{
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	//检验输入账号密码
		public void EditRegistNamePasswd()
		{
			name=username.getText().toString().trim();
			password=passwd.getText().toString().trim();
			if(!name.equals(""))
			{
				if(!password.equals(""))
				{
					Regist(name, password);
				}
				else
				{
					Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}
			}else
			{
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			}
		}
		
	//登陆验证
	public void Login(String name ,String passwd)
	{
		//login
		HttpGetClient.getInstance().getLoginFromNet(
				name,passwd);
		HttpGetClient.getInstance().setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(String result) {
						// 解析登录接口回来数据
						Log.i(TAG,"result:"+result.toString());
						Message msg = new Message();
						if(result.equals("error"))
						{
							//网络链接失败
							msg.what=10;
						}
						else
						{
							int bresult = XmlParser.getInstance()
									.parserLoginDetail(result);
							
							
							
							if(bresult!=-1)
							{
								msg.what=0;
								msg.arg1=bresult;
							}
							else
							{
								msg.what=1;
							}
						}
						
						Bundle mBundle = new Bundle();
						msg.setData(mBundle);
						mHandler.sendMessage(msg);
					}
				});
	}
	
	//注册
		public void Regist(String name ,String passwd)
		{
			registName=name;
			registPassword=passwd;
			//REGIST
			HttpGetClient.getInstance().getRegistFromNet(
					name,passwd);
			HttpGetClient.getInstance().setOnCompleteListener(//服务器与客户端http数据交互
					new OnCompleteListener() {
						@Override
						public void onComplete(String result) {
							// 解析登录接口回来数据
							Log.i(TAG,"result:"+result.toString());
							Message msg = new Message();
							if(result.equals("error"))
							{
								//网络链接失败
								msg.what=10;
							}
							else
							{
								boolean bresult = XmlParser.getInstance()
										.parserRegistDetail(result);
								
								
								if(bresult)
								{
									msg.what=2;
								}
								else
								{
									msg.what=3;
								}
							}
							Bundle mBundle = new Bundle();
							msg.setData(mBundle);
							mHandler.sendMessage(msg);
						}
					});
		}
	
	//界面初始
	public void CheckUserData()
	{
		preferences=getSharedPreferences("zhaopin_userinfo", MODE_PRIVATE);
		String name=preferences.getString("name", null);
		String result=preferences.getString("result", null);
		if(name!=null&&!"".equals(name))
		{
			username.setText(""+name);
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			EditNamePasswd();
			break;
		case R.id.regist:
			//regist
			EditRegistNamePasswd();
			break;
		default:
			break;
		}
	}

	//监听服务器传回来的结果
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(TAG,"handleMessage:"+"handleMessage");
			if(msg.what==0)
			{
				//login
				
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
				preferences=getSharedPreferences("zhaopin_userinfo", MODE_PRIVATE);
				editor=preferences.edit();
				editor.putString("name", name);
				editor.putString("passwd", password);
				editor.putInt("id", msg.arg1);
				editor.commit();
				Intent intent=new Intent(getApplication(), ZhaoPinListActivity.class);
				startActivity(intent);
			}
			else if(msg.what==1)
			{
				//login
			Toast.makeText(LoginActivity.this, "登录失败，账号或者密码错误", Toast.LENGTH_SHORT).show();
			
			}
			else if(msg.what==2)
			{
				//regist
				Toast.makeText(LoginActivity.this, "注册完成!用户名："+registName+",密码:"+registPassword, Toast.LENGTH_SHORT).show();
			}
			else if(msg.what==3)
			{
				//regist
				Toast.makeText(LoginActivity.this, "注册失败!", Toast.LENGTH_SHORT).show();
			}
			else if(msg.what==10)
			{
				//regist
				Toast.makeText(LoginActivity.this, "网络连接服务器失败!", Toast.LENGTH_SHORT).show();
			}
		
			
		
		
     };
};

		

	}
	

