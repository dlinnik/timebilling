package ru.timebilling.persistance;

/**
 * хранит текущее значение appName в ThreadLocal
 * @author vshmelev
 *
 */
public class AppNameSupplier {
	
	private static final ThreadLocal<String> applicationHolder = new ThreadLocal<String>();


	public static String getAppName() {
		return applicationHolder.get();
	}

	public static void setAppName(String value) {
		applicationHolder.set(value);
	}
	
	

}
