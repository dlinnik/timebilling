package ru.timebilling.model;

import java.net.URL;

public class RequestURLSupplier {

	public final static String URL_LOGIN = "/url_login";
	public final static String URL_LOGIN_UT = "ut";
	
	private static final ThreadLocal<URL> threadLocalHolder = new ThreadLocal<URL>();


	public static URL getURL() {
		return threadLocalHolder.get();
	}

	public static void setURL(URL value) {
		threadLocalHolder.set(value);
	}
	
	public static String getBaseURL(){
		URL url = getURL();
		StringBuilder sb = new StringBuilder(url.getProtocol()).append("://").
				append(AppNameSupplier.getAppName()).append(".").append(url.getHost());
		if(url.getPort()!=80){
			sb.append(":").append(url.getPort());
		}
		return sb.toString();
	}
}
