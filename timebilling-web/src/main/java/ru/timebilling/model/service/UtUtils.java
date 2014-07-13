package ru.timebilling.model.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;

import ru.timebilling.model.UserPassCredentials;

/**
 * various token/password specific methods
 * @author vshmelev
 *
 */
public class UtUtils {
	
	private transient static int SALT_STR_LENGTH = 4;
	
	/**
	 * can be used for passwords generation
	 * @param length
	 * @return password string
	 */
	public static String generateRandomString(int length){
		return RandomStringUtils.random(length, true, true);
	}
		
	public static String encodeUserPassCredentials(UserPassCredentials up){
		String salt = generateRandomString(SALT_STR_LENGTH);
		String creds = up.getUserName() + ":" + up.getPassword() + salt;
		return Base64.encodeBase64String(creds.getBytes()).trim();
	}

	public static UserPassCredentials decodeUserPassCredentials(String ut){
		String creds = new String(Base64.decodeBase64(ut));
		creds = creds.substring(0, creds.length()-4);
		return new UserPassCredentials(creds.substring(0, creds.indexOf(":")), 
				creds.substring(creds.indexOf(":")+1));
	}
	
	public static void main(String[] args){
		String userName = "admin";
		String pass = generateRandomString(8);
		String creds2Send = encodeUserPassCredentials(new UserPassCredentials(userName, pass));
		
		System.out.println(userName + ":" + pass);
		System.out.println("\"" + creds2Send + "\"");
		UserPassCredentials credsReceived = decodeUserPassCredentials(creds2Send);
		System.out.println(credsReceived.getUserName() + ":\"" + credsReceived.getPassword() + "\"");

	}
	
}
