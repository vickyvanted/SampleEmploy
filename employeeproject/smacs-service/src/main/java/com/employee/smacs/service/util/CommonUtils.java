package com.employee.smacs.service.util;

import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CommonUtils {

	public CommonUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("Hi....");
		isValidEpochDate(1588996349000L);
		//System.out.println("Check"+ isValidEpochDate(1588996349000L));
	}

	
	public static boolean isValidEpochDate(Long epochtime){
		System.out.println("d:");
		Date d = new Date(epochtime);
		System.out.println("d:"+d);
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		cal.setTime(d);
		System.out.println("cal:"+cal);
		try {
		    cal.getTime();
		    log.debug("isValidEpochDate(){},{}",epochtime,true);
		    return true;
		}
		catch (Exception e) {
		  log.debug("isValidEpochDate(){},{}",epochtime,false);
		  return false;
		}
	}

}
