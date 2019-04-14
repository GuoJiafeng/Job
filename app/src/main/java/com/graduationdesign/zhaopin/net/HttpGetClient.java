/**
 * 
 */
package com.graduationdesign.zhaopin.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.graduationdesign.zhaopin.model.Resume;

import android.util.Log;

/**
 * @author John
 * 
 */
public class HttpGetClient {

	/**
	 * 监听器
	 */
	private OnCompleteListener onCompleteListener;
	/**
	 * 接口地址
	 */
	private String baseUrl = "http://192.168.5.208:8080";
	/**
	 * http请求返回体
	 */
	private HttpResponse httpResponse;
	/**
	 * 请求结果
	 */
	private String resultString;

	private static HttpGetClient instance;

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static HttpGetClient getInstance() {
		if (instance == null) {
			instance = new HttpGetClient();
		}
		return instance;
	}

	/**
	 * 设置请求完成接口
	 * 
	 * @param onCompleteListener
	 */
	public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
		this.onCompleteListener = onCompleteListener;
	}

	/**
	 * 获取接口
	 * 
	 * @param name
	 *            车牌
	 * @param pwd
	 *            密码
	 * @param methodName
	 *            接口方法名
	 */
	public void getCarWeiZhangInfoListFromNet(final String carNumber,final String methodName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=recruitList&pageNo=1";
				
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	/**
	 * 获取招聘列表接口
	 */
	public void getZhaoPinListFromNet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=recruitList&pageNo=1";
				Log.i("http","url:"+url.toString());
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					Log.i("http","result:"+httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						Log.i("http","result:"+resultString.toString());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	
	/**
	 * 获取搜索招聘列表接口
	 */
	public void getSearchZhaoPinListFromNet(final String keyName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=recruitList&search_jobName="+keyName;
				Log.i("http","url:"+url.toString());
				HttpGet post = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(post);
					Log.i("http","result:"+httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						Log.i("http","result:"+resultString.toString());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	/**
	 * 获取面试列表接口
	 */
	public void getZhaoInterviewFromNet(final int id) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=interviewList&userId="+id+"&pageNo=1";
				Log.i("http","url:"+url.toString());
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					Log.i("http","result:"+httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						Log.i("http","result:"+resultString.toString());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	/**
	 * 获取招聘详情接口
	 * 
	 * @param name
	 *            招聘id

	 */
	public void getZhaoPinDetailFromNet(final String id) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=recruit&id="+id;
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	
	/**
	 * 获取简历接口
	 * 
	 * @param name
	 * 用户id

	 */
	public void getResumeFromNet(final int id) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=resume&id="+id;
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	/**
	 * 更新接口
	 * 
	 * @param name
	 * 用户id

	 */
	public void upDateResumeFromNet(final Resume resume) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=resumeUpdate";
				HttpPost post = new HttpPost(url);
				List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>(); 
				BasicNameValuePair param0 = new BasicNameValuePair("userId",resume.getUserID()+"");
				BasicNameValuePair param1 = new BasicNameValuePair("address",resume.getAddress());
				BasicNameValuePair param2 = new BasicNameValuePair("age",resume.getAge()+"");
				BasicNameValuePair param3 = new BasicNameValuePair("education",resume.getXueli()+"");
				BasicNameValuePair param4 = new BasicNameValuePair("evaluation",resume.getZiwopinjia());
				BasicNameValuePair param5 = new BasicNameValuePair("major",resume.getZhuanye());
				BasicNameValuePair param6 = new BasicNameValuePair("marriage",resume.getHunying()+"");
				BasicNameValuePair param7 = new BasicNameValuePair("name",resume.getName());
				BasicNameValuePair param8 = new BasicNameValuePair("phone",resume.getLianxifangshi());
				BasicNameValuePair param9 = new BasicNameValuePair("politics",resume.getZhengzhi()+"");
				BasicNameValuePair param10 = new BasicNameValuePair("salaryRange",resume.getSalaryRange()+"");
				BasicNameValuePair param11 = new BasicNameValuePair("school",resume.getXuexiao());
				BasicNameValuePair param12 = new BasicNameValuePair("sex",resume.getSex()+"");
				BasicNameValuePair param13 = new BasicNameValuePair("workExperience",resume.getGongzuojingli());
				BasicNameValuePair param14 = new BasicNameValuePair("workLife",resume.getNianxian()+"");
				paramList.add(param0); 
				paramList.add(param1); 
				paramList.add(param2); 
				paramList.add(param3); 
				paramList.add(param4); 
				paramList.add(param5); 
				paramList.add(param6); 
				paramList.add(param7); 
				paramList.add(param8); 
				paramList.add(param9); 
				paramList.add(param10); 
				paramList.add(param11); 
				paramList.add(param12); 
				paramList.add(param13); 
				paramList.add(param14); 
				try {
					post.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8)); 
					HttpClient client = new DefaultHttpClient();
					httpResponse = client.execute(post);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	
	/**
	 * 投简历
	 * 
	 * @param name
	 * 用户id,简历id

	 */
	public void getPostResumeFromNet(final int id,final int resumeID) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=candidate&userId="+id+"&recruitId="+resumeID;
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
	
	/**
	 * 登录
	 * 
	 * @param name
	 *  用户名 密码

	 */
	public void getLoginFromNet(final String userName,final String  pass) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=login&username="+userName+"&password="+pass;
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
					else
					{
						Log.i("network","INFO:network error");
					}
				} catch (Exception e) {
					//网络连接失败
					Log.i("network","INFO:network error Exception");
					onCompleteListener.onComplete("error");
				}
			}
		}).start();
	}
	
	/**
	 * 注册
	 * 
	 * @param name
	 *  用户名 密码

	 */
	public void getRegistFromNet(final String userName,final String  pass) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = baseUrl + "/Job/ApiServlet" + "?" + "action=register&username="+userName+"&password="+pass;
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				try {
					httpResponse = client.execute(get);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						resultString = EntityUtils.toString(httpResponse
								.getEntity());
						onCompleteListener.onComplete(resultString);
					}
				} catch (Exception e) {
					onCompleteListener.onComplete("error");
				}
			}
		}).start();
	}

}
