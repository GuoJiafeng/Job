/**
 * 
 */
package com.graduationdesign.zhaopin.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.graduationdesign.zhaopin.model.Resume;
import com.graduationdesign.zhaopin.model.ZhaoPinInfo;


/**
 * @author John
 * 
 */
public class XmlParser {

	/**
	 * 
	 */
	private static XmlParser instance;

	/**
	 * 
	 * @return
	 */
	public static XmlParser getInstance() {

		if (instance == null) {
			instance = new XmlParser();
		}
		return instance;
	}

	

	



	
	/**
	 * 解析招聘列表
	 * 
	 * @param resultString
	 * @return
	 */
	
	public ArrayList<ZhaoPinInfo> parserZhaoPinInfoList(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

			ArrayList<ZhaoPinInfo> mList = null;
			ZhaoPinInfo zhaopin = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					mList = new ArrayList<ZhaoPinInfo>();
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("recruit")) {
						zhaopin = new ZhaoPinInfo();
					} else if (zhaopin != null) {
						if (name.equalsIgnoreCase("companyName")) {
							zhaopin.setCompany(parser.nextText());
						} 
						else if (name.equalsIgnoreCase("jobName")) {
							zhaopin.setPositionInfo(parser.nextText());// 如果后面是Text元素,即返回它的值
						}
						else if (name.equalsIgnoreCase("startDate")) {
							zhaopin.setDate(parser.nextText());
						} else if (name.equalsIgnoreCase("workPlace")) {
							zhaopin.setPlace(parser.nextText());
						} else if (name.equalsIgnoreCase("id")) {
							try{
								zhaopin.setZhaoPinID(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								break;
							}
							
						}
						
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					if (parser.getName().equalsIgnoreCase("recruit")
							&& zhaopin != null) {
						mList.add(zhaopin);
						zhaopin = null;
					}
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return mList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 解析面试列表
	 * 
	 * @param resultString
	 * @return
	 */
	
	public ArrayList<ZhaoPinInfo> parserInterViewInfoList(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

			ArrayList<ZhaoPinInfo> mList = null;
			ZhaoPinInfo zhaopin = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					mList = new ArrayList<ZhaoPinInfo>();
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("recruit")) {
						zhaopin = new ZhaoPinInfo();
					} else if (zhaopin != null) {
						if (name.equalsIgnoreCase("companyName")) {
							zhaopin.setCompany(parser.nextText());
						} 
						else if (name.equalsIgnoreCase("jobName")) {
							zhaopin.setPositionInfo(parser.nextText());// 如果后面是Text元素,即返回它的值
						}
						else if (name.equalsIgnoreCase("companyAddress")) {
							zhaopin.setAddress(parser.nextText());
						} 
						 else if (name.equalsIgnoreCase("id")) {
							try{
								zhaopin.setZhaoPinID(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								break;
							}
							
						}
						
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					if (parser.getName().equalsIgnoreCase("recruit")
							&& zhaopin != null) {
						mList.add(zhaopin);
						zhaopin = null;
					}
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return mList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析招聘详情
	 * 
	 * @param resultString
	 * @return
	 */
	//http://trafficviolationserver.duapp.com/ApiTicketServlet?q=list&licensePlate=%E8%8B%8FA00001
	public ZhaoPinInfo parserZhaoPinDetail(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

		
			ZhaoPinInfo zhaopin = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("recruit")) {
						zhaopin = new ZhaoPinInfo();
					} else if (zhaopin != null) {
						if (name.equalsIgnoreCase("companyAddress")) {
							zhaopin.setAddress(parser.nextText());
						} else if (name.equalsIgnoreCase("companySize")) {
							zhaopin.setGuimo(parser.nextText());// 如果后面是Text元素,即返回它的值
						}
						else if (name.equalsIgnoreCase("companyType")) {
							zhaopin.setXinzhi(parser.nextText());// 如果后面是Text元素,即返回它的值
						}
						else if (name.equalsIgnoreCase("education")) {
							zhaopin.setXueli(parser.nextText());
						} else if (name.equalsIgnoreCase("jobDuty")) {
							zhaopin.setZhize(parser.nextText());
						}
						else if (name.equalsIgnoreCase("jobQualification")) {
							zhaopin.setZige(parser.nextText());
						}
						else if (name.equalsIgnoreCase("jobTreatment")) {
							zhaopin.setDaiyu(parser.nextText());
						}
						else if (name.equalsIgnoreCase("recruitNumber")) {
							zhaopin.setRenshu(parser.nextText());
						}
						else if (name.equalsIgnoreCase("salaryRange")) {
							zhaopin.setXinshui(parser.nextText());
						}
						else if (name.equalsIgnoreCase("workLife")) {
							zhaopin.setNianxian(parser.nextText());
						}
						
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					if (parser.getName().equalsIgnoreCase("recruit")
							&& zhaopin != null) {
						//mList.add(weizhang);
						//weizhang = null;
					}
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return zhaopin;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 解析简历详情
	 * 
	 * @param resultString
	 * @return
	 */
	public Resume parserResumeDetail(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

		
			Resume resume = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("resume")) {
						resume = new Resume();
					} else if (resume != null) {
						if (name.equalsIgnoreCase("address")) {
							resume.setAddress(parser.nextText());
						} else if (name.equalsIgnoreCase("age")) {
							try{
								resume.setAge(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setAge(0);
							}
							
						}
						else if (name.equalsIgnoreCase("education")) {
							try{
								resume.setXueli(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setXueli(1);
							}
						}
						else if (name.equalsIgnoreCase("evaluation")) {
							resume.setZiwopinjia(parser.nextText());
						} else if (name.equalsIgnoreCase("major")) {
							resume.setZhuanye(parser.nextText());
						}
						else if (name.equalsIgnoreCase("marriage")) {
							try{
								resume.setHunying(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setHunying(1);
							}
							
						}
						else if (name.equalsIgnoreCase("name")) {
							resume.setName(parser.nextText());
						}
						else if (name.equalsIgnoreCase("phone")) {
							resume.setLianxifangshi(parser.nextText());
						}
						else if (name.equalsIgnoreCase("politics")) {
							try{
								resume.setZhengzhi(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setZhengzhi(1);
							}
							
						}
						else if (name.equalsIgnoreCase("salaryRange")) {
							try{
								resume.setSalaryRange(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setSalaryRange(0);
							}
						}
						else if (name.equalsIgnoreCase("school")) {
							resume.setXuexiao(parser.nextText());
						}
						else if (name.equalsIgnoreCase("sex")) {
							try{
								resume.setSex(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setSex(1);
							}
						}
						else if (name.equalsIgnoreCase("workExperience")) {
							resume.setGongzuojingli(parser.nextText());
						}
						else if (name.equalsIgnoreCase("workLife")) {
							try{
								resume.setNianxian(Integer.parseInt((parser.nextText())));
							}
							catch(Exception e)
							{
								resume.setNianxian(1);
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					if (parser.getName().equalsIgnoreCase("resume")
							&& resume != null) {
					}
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return resume;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 解析更新简历详情
	 * 
	 * @param resultString
	 * @return
	 */
	public boolean parserUpdateResumeDetail(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

		    boolean result=false;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("err_code")) {
					     if(parser.nextText().equals("0"))
					     {
					    	 return true;
					     }
					     else
					     {
					    	 return false;
					     }
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 解析登录详情
	 * 
	 * @param resultString
	 * @return
	 */
	public int parserLoginDetail(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

		    int result=-1;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("id")) {
						try{
							int i=(Integer.parseInt((parser.nextText())));
							return i;
						}
						catch(Exception e)
						{
							return -1;
						}
					    
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	
	/**
	 * 解析注册详情
	 * 
	 * @param resultString
	 * @return
	 */
	public boolean parserRegistDetail(String resultString) {
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(
				resultString.getBytes());
		try {
			parser.setInput(inputStream, "UTF-8");
			int eventType = parser.getEventType();

			boolean result=false;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					
					break;

				case XmlPullParser.START_TAG:// 开始元素事件
					String name = parser.getName();
					if (name.equalsIgnoreCase("err_code")) {
						if(parser.nextText().equals("0"))
						{
							return true;
						}
						else
						{
							return false;
						}
					    
					}
					break;
				case XmlPullParser.END_TAG:// 结束元素事件
					
					break;
				}
				eventType = parser.next();
			}
			inputStream.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
