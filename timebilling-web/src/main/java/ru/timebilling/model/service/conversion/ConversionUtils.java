package ru.timebilling.model.service.conversion;

import java.util.Calendar;
import java.util.Date;

/**
 * конвертации
 * @author vshmelev
 *
 */
public class ConversionUtils {
	
	  public static java.sql.Date convertToSQLDate(Date date){
		  java.util.Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  cal.set(Calendar.HOUR_OF_DAY, 0);
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MILLISECOND, 0);    
		  return new java.sql.Date(cal.getTime().getTime());
	  }
	  

}
