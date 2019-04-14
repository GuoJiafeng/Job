package com.graduationdesign.zhaopin.activity.zhaopin;



import java.util.ArrayList;

import com.graduationdesign.zhaopin.ApplicationInstance;
import com.graduationdesign.zhaopin.R;

import com.graduationdesign.zhaopin.model.Resume;
import com.graduationdesign.zhaopin.model.ZhaoPinInfo;
import com.graduationdesign.zhaopin.net.HttpGetClient;
import com.graduationdesign.zhaopin.net.OnCompleteListener;
import com.graduationdesign.zhaopin.parser.XmlParser;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
 * AddResumeActivity
 * 增加简历页面
 */
public class AddResumeActivity extends Activity implements OnClickListener 
{
	private static final String TAG = "AddResumeActivity";


	int userID=4;
	Resume resume;
	private EditText nameEditText;
	private TextView submitText;
	private TextView resetText;


	Spinner nianxianSpinner;
	Spinner sexSpinner;
	EditText ageEditText;
	Spinner hunyingSpinner;
	Spinner zhenzhiSpinner;
	EditText addressEditText;
	EditText phoneEditText;
	Spinner xueliSpinner;
	EditText xuexiaoEditText;
	EditText zhuanyeEditText;
	Spinner yuexinSpinner;
	EditText ziwopingjiaEditText;
	EditText gongzuojingyanEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_resume_layout);

		nameEditText=((EditText)findViewById(R.id.name));
		submitText=((TextView)findViewById(R.id.base_submit));
		resetText=((TextView)findViewById(R.id.base_clear));
		//获取intent数据

		resume=new Resume();

		// 初始化控件
		nianxianSpinner = (Spinner) findViewById(R.id.nianxian);
		//nianxianSpinner.setSelection(1); 
		// 建立数据源
		//String[] mItems = getResources().getStringArray(R.array.nianxians);
		// 建立Adapter并且绑定数据源
		//ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		//绑定 Adapter到控件
		//mSpinner.setAdapter(_Adapter);
		nianxianSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				resume.setNianxian(position);
				//String str=parent.getItemAtPosition(position).toString();
				//Toast.makeText(AddResumeActivity.this, "你点击的是:"+str, 2000).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});



		// 初始化控件
		sexSpinner = (Spinner) findViewById(R.id.sex);
		sexSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				resume.setSex(position+1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		ageEditText=(EditText) findViewById(R.id.age);


		hunyingSpinner= (Spinner) findViewById(R.id.hunying);
		hunyingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				resume.setHunying(position+1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});


		zhenzhiSpinner= (Spinner) findViewById(R.id.zhengzhi);
		zhenzhiSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				resume.setZhengzhi(position+1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		addressEditText=(EditText) findViewById(R.id.address);;
		phoneEditText=(EditText) findViewById(R.id.lianxi);;
		xueliSpinner= (Spinner) findViewById(R.id.xueli);
		xueliSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG,"position:"+position);
				resume.setXueli(position+1);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		xuexiaoEditText=(EditText) findViewById(R.id.xuexiao);;
		zhuanyeEditText=(EditText) findViewById(R.id.zhuanye);;
		yuexinSpinner=(Spinner) findViewById(R.id.yuexin);
		yuexinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				resume.setSalaryRange(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		ziwopingjiaEditText=(EditText) findViewById(R.id.ziwopingjia);;
		gongzuojingyanEditText=(EditText) findViewById(R.id.gongzuojingyan);;




		initView(savedInstanceState);

	}

	private void initView(Bundle savedInstanceState) 
	{
		((TextView)findViewById(R.id.titleName)).setText("完善简历");

		findViewById(R.id.imageViewBack).setOnClickListener(this);
		findViewById(R.id.base_submit).setOnClickListener(this);

		SharedPreferences preferences=getSharedPreferences("zhaopin_userinfo", MODE_PRIVATE);

		userID=preferences.getInt("id", 0);

		resetText.setOnClickListener(this);

		HttpGetClient.getInstance().getResumeFromNet(
				userID);
		HttpGetClient.getInstance().setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(String result) {
						// 解析登录接口回来数据
						Log.i(TAG,"result:"+result.toString());
						Resume resumeTemp = XmlParser.getInstance()
								.parserResumeDetail(result);
						resume.setName(resumeTemp.getName());
						resume.setAddress(resumeTemp.getAddress());
						resume.setLianxifangshi(resumeTemp.getLianxifangshi());
						resume.setXuexiao(resumeTemp.getXuexiao());
						resume.setZhuanye(resumeTemp.getZhuanye());
						resume.setZiwopinjia(resumeTemp.getZiwopinjia());
						resume.setGongzuojingli(resumeTemp.getGongzuojingli());

						resume.setAge(resumeTemp.getAge());
						resume.setXueli(resumeTemp.getXueli());
						resume.setHunying(resumeTemp.getHunying());
						resume.setZhengzhi(resumeTemp.getZhengzhi());
						resume.setSalaryRange(resumeTemp.getSalaryRange());
						resume.setSex(resumeTemp.getSex());
						resume.setNianxian(resumeTemp.getNianxian());
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
				if(resume!=null)
				{
					nameEditText.setText(resume.getName());
					nianxianSpinner.setSelection(resume.getNianxian());
					sexSpinner.setSelection(resume.getSex()-1);
					ageEditText.setText(resume.getAge()+"");
					hunyingSpinner.setSelection(resume.getHunying()-1);
					zhenzhiSpinner.setSelection(resume.getZhengzhi()-1);
					addressEditText.setText(resume.getAddress());
					phoneEditText.setText(resume.getLianxifangshi());
					xueliSpinner.setSelection(resume.getXueli()-1);
					xuexiaoEditText.setText(resume.getXuexiao());
					zhuanyeEditText.setText(resume.getZhuanye());
					yuexinSpinner.setSelection(resume.getSalaryRange());
					ziwopingjiaEditText.setText(resume.getZiwopinjia());
					gongzuojingyanEditText.setText(resume.getGongzuojingli());
				}

			}
			else if(msg.what==1)
			{
				//update
				Toast.makeText(AddResumeActivity.this, "更新完成!", Toast.LENGTH_SHORT).show();
			}
			else if(msg.what==1)
			{
				//update
				Toast.makeText(AddResumeActivity.this, "更新失败!", Toast.LENGTH_SHORT).show();
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
			//保存按钮
		case R.id.base_submit:
			//校验
			if(nameEditText.getText().toString().equals("")||
					ageEditText.getText().toString().equals("")||
					addressEditText.getText().toString().equals("")||
					phoneEditText.getText().toString().equals("")||
					xuexiaoEditText.getText().toString().equals("")||
					zhuanyeEditText.getText().toString().equals("")||
					ziwopingjiaEditText.getText().toString().equals("")||
					gongzuojingyanEditText.getText().toString().equals(""))
			{
				Toast.makeText(AddResumeActivity.this, "请填写完成", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Log.i(TAG,"resume:"+"handleMessage");
				//更新简历
				resume.setUserID(userID);
				resume.setName(nameEditText.getText().toString());
				try{
					resume.setAge(Integer.parseInt(ageEditText.getText().toString()));
				}
				catch(Exception e)
				{
					Toast.makeText(AddResumeActivity.this, "年龄为整数", Toast.LENGTH_SHORT).show();
					break;
				}

				resume.setAddress(addressEditText.getText().toString());
				resume.setLianxifangshi(phoneEditText.getText().toString());
				resume.setXuexiao(xuexiaoEditText.getText().toString());
				resume.setZhuanye(zhuanyeEditText.getText().toString());
				resume.setZiwopinjia(ziwopingjiaEditText.getText().toString());
				resume.setGongzuojingli(gongzuojingyanEditText.getText().toString());

				Log.i(TAG,"resume:"+resume.toString());
				HttpGetClient.getInstance().upDateResumeFromNet(
						resume);
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

			break;
			// 重置按钮
		case R.id.base_clear:
			nameEditText.setText("");
			break;
		}


	}

	//跳转至此activity
	public static void startActivity(Context context,int userID)
	{

		Intent intent = new Intent(context, AddResumeActivity.class);
		//保存
		//((ApplicationInstance)context.getApplicationContext()).setCarList(carInfosList);
		context.startActivity(intent);
	}







}
