package com.employee.smacs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
	
	
	public static boolean isToday(long epochTime){
		Calendar now = Calendar.getInstance();
		Calendar timeToCheck = Calendar.getInstance();
		timeToCheck.setTimeInMillis(epochTime);

		if(now.get(Calendar.YEAR) == timeToCheck.get(Calendar.YEAR)) {
		    if(now.get(Calendar.DAY_OF_YEAR) == timeToCheck.get(Calendar.DAY_OF_YEAR)){
		    	return true;
		    }
		}
		return false;
	}
	
	public static long getTodayInEpoch(){
		Calendar c = Calendar.getInstance();
		Date d = new Date();
		c.setTime(d);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    return c.getTimeInMillis();		
	}
	
	@SuppressWarnings("deprecation")
	public static String getStringTime(long timeStamp){
		Date d = new Date(timeStamp);
		int h = d.getHours();
		int  m = d.getMinutes();
		int s = d.getSeconds();
		String format="AM";
		System.out.println(h);
		String hr = (h <= 9)?('0'+ String.valueOf(h)):String.valueOf(h);
		String min = (m <= 9)?('0'+String.valueOf(m)):String.valueOf(m);
		String sec = (s <= 9)?('0'+String.valueOf(s)):String.valueOf(s);
		if(h>12){
			h=h-12;
			hr = String.valueOf(h);
			format = "PM";
		}else if(h==12){
			format = "PM";
		}else if(h==0){
			h=12;
			hr = String.valueOf(h);
		}
		return hr+":"+min+":"+sec+" "+format;		
	}
	
	/**
	 * Returns the date at 12:00am local timestam in epoch
	 * @param timeStamp
	 * @return
	 */
	public static long getDateInEpoch(long timeStamp){
		Calendar c = Calendar.getInstance();
		Date d = new Date(timeStamp);
		c.setTime(d);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    return c.getTimeInMillis();		
	}
	
	public static int getMinOfTheDay(long endDate){
		
		
		long startDate= getTodayInEpoch();
		//milliseconds
		long different = endDate - startDate;
		
		System.out.println("startDate : " + startDate);
		System.out.println("endDate : "+ endDate);
		System.out.println("different : " + different);
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;
		
		int elapsedHours = (int) (different / hoursInMilli);
		different = different % hoursInMilli;
		
		int elapsedMinutes = (int) (different / minutesInMilli);
		different = different % minutesInMilli;
		
		
		
		return elapsedHours*60 + elapsedMinutes;
	
	}
	
	public static void main(String args[]) {
		/*long today= new Date().getTime();
		System.out.println("Today in Epoch: " + getTodayInEpoch() +" is currentDay:" + isToday(today));
		System.out.println("Get Today in Epoch: " + getDateInEpoch(new Date().getTime()) );
		
		System.out.println("Get Min since: " + getMinOfTheDay(new Date().getTime()) ); 1550809800000L*/

		String str = getStringTime(1543550435000L);
		System.out.println(str);
		getSqlDateFormat(new Date().getTime());
	}
	
	//return Epoch to SQL date format.
	public static String getSqlDateFormat(Long epochTime){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date(epochTime);
		String strDate= formatter.format(d);  
	    log.debug("getSqlDateFormat(){},{}",epochTime,strDate);
		return strDate;
	}

}
