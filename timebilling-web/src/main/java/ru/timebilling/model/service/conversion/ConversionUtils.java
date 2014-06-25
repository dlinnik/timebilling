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
	  
	  public static java.sql.Date getMinDate(java.sql.Date d1, java.sql.Date d2){
		  if(d1 == null){
			  return d2;
		  }
		  
		  if(d1.compareTo(d2) > 0){
			  return d2;
		  }
		  
		  return d1;
	  }

	  public static java.sql.Date getMaxDate(java.sql.Date d1, java.sql.Date d2){
		  if(d1 == null){
			  return d2;
		  }
		  
		  if(d1.compareTo(d2) > 0){
			  return d1;
		  }
		  
		  return d2;
	  }
	  
		public static String wrapForLikeClause(String s){
			return "%" + s + "%";
		}



}
