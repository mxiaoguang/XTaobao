package com.xiaoguang.xtaobao.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * get any infomation about phone from this class
 * @author liu
 */
public class PhoneUtil {
	public static int screenHeight;
	public static int screenWidth;
	public static int unitHeight;
	public static int sbar;
	public static int itemHeight;
	
	public static void init(Context context){
		int[] resolution=getResolution(context);
		screenWidth=resolution[0];
		screenHeight=resolution[1];
		unitHeight=screenHeight/10;
		sbar = getSbar(context);
		itemHeight=screenHeight-2*unitHeight-sbar;
	}
	
    public double getDensity(Context context){
    	DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    	return displayMetrics.density;
    }
    
    /**
     * get resolution
     * @param context
     * @return
     */
    public static int[] getResolution(Context context){
    	int resolution[]=new int[2];
    	DisplayMetrics dm = new DisplayMetrics();
    	PhoneManager.getWindowManger(context).getDefaultDisplay().getMetrics(dm);
    	resolution[0]=dm.widthPixels;
    	resolution[1]=dm.heightPixels;
    	return resolution;
    }
    
	/**
	 * get Imsi(International Mobile Subscriber Identification Number)
	 * @param context
	 * @return Imsi
	 */
	public static String getImsi(Context context){
		String imsi = PhoneManager.getTelephonyManager(context).getSubscriberId();
		return imsi;
	}
    
	public static String getDeviceId(Context context){
		String deviceId=PhoneManager.getTelephonyManager(context).getDeviceId();
		return deviceId;
	}
	
	/**
	 * ��ȡ��վ��λ��Ϣ
	 * ��������Ϊ int[0]=cid int[1]=lac 
	 * @param context
	 */
    public static int[] getCellLocation(Context context){
    	int[] i=new int[2];
    	String simType=getPhoneType(context);
    	if(simType.equals("GSM")){
    		GsmCellLocation gsm = (GsmCellLocation) PhoneManager.getTelephonyManager(context).getCellLocation();
    		i[0] = gsm.getCid();  
    		i[1] = gsm.getLac();  
    	}else{
    		 CdmaCellLocation cdma = (CdmaCellLocation) PhoneManager.getTelephonyManager(context).getCellLocation();
    		 i[0] = cdma.getBaseStationId(); 
    		 i[1] = cdma.getNetworkId(); 
    	}
    	return i;
    }
    
    /**��ȡ�������� GSM ���� CDMA*/
	public static String getPhoneType(Context context) {
		String simType="NONE";
		TelephonyManager tm = PhoneManager.getTelephonyManager(context);
		int phoneType = tm.getPhoneType();
		if (phoneType == TelephonyManager.PHONE_TYPE_GSM) {
			simType = "GSM";
		} else if (phoneType == TelephonyManager.PHONE_TYPE_CDMA) {
			simType = "CDMA";
		} else {
			simType = "NONE";
		}
		return simType;
	}
    
	/**
	 * �ж��Ƿ�Ϊƽ��
	 * @return
	 */
	public static boolean isPad(Context context) {
		WindowManager wm = PhoneManager.getWindowManger(context);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		// ��Ļ�ߴ�
		double screenInches = Math.sqrt(x + y);
		// ����6�ߴ���ΪPad
		if (screenInches >= 6.0) {
			return true;
		}
		return false;
	}
    
	/**
	 * get fit width for different resolution,input param is width for 720
	 * @param width
	 */
	public static int getFitWidth(Context context, int inwidth){
		if(context==null)
			return inwidth;
		int screenwidth=getResolution(context)[0];
		return (inwidth*screenwidth)/720;
	}
	
	/**
	 * get fit width for different resolution,input param is width for 1180
	 * @param width
	 */
	public static int getFitHeight(Context context, int inheight){
		if(context==null)
			return inheight;
		int screenheight=getResolution(context)[1];
		return (inheight*screenheight)/1280;
	}
	
	public static int getFitTextSize(Context context, int textsize){
		if(context==null)
			return textsize;
		int screenheight=getResolution(context)[1];
		return (textsize*screenheight)/1280;
	}
	
	public static int getSbar(Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
		    c = Class.forName("com.android.internal.R$dimen");
		    obj = c.newInstance();
		    field = c.getField("status_bar_height");
		    x = Integer.parseInt(field.get(obj).toString());
		    sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
		    e1.printStackTrace();
		} 
		return sbar;
	}
	
	public static void showToast(final Context context, final String msg){
		((Activity)context).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
