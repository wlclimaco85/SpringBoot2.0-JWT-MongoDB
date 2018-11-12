
package com.freebies.common;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class EncryptionUtility {
	
	private static final String key = "fB<[;.7e/OL0R!k|";

	
	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

		}

		return sb.toString();
	}
	
	

	public static String encryptPwd(String password) {
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		String encryptPassword=passwordEncoder.encode(password);
		return encryptPassword;
	}
	
	
	
	public static boolean matchPassword(String password,String encrytPassword) {
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();		
		return passwordEncoder.matches(password, encrytPassword);
	}
	
	
	public static String encrypt( String text ){
		
		try {
			
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	       
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	       
	        byte[] encrypt= cipher.doFinal(text.getBytes());
	        
	        String encrypted = new String( Base64.getEncoder().encodeToString(encrypt).getBytes("utf-8")); 
	        
	        return encrypted;
        
		} catch( Exception e){
			
			return null;
		}
	}
	
	public static String  decrypt( String encrypted ){
		
		try{
			 Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			 Cipher cipher = Cipher.getInstance("AES");
			 
			 cipher.init(Cipher.DECRYPT_MODE, aesKey);
	         byte[] recoveredBytes = cipher.doFinal( Base64.getDecoder().decode( encrypted) );

	         String decrypted = new String(recoveredBytes);

	         return decrypted;
		} catch( Exception e){
			return null;
		}
	}
	
}
