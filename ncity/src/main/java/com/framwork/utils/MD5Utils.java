package com.framwork.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Utils {
	private static final String TOKEN = "@1#2$34%ds^we2&32*";//加密令牌
	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',  'E', 'F'};
	private static final int MD5_TEN = 10;
	public static void main(String[] args) {
		System.out.println(getMD5("123456"));
	}
	
	public static String getMD5ByToken(String text){
		return getMD5ByToken(text, TOKEN);
	}
	
	public static String getMD5ByToken(String text, String token){
		try {
			return getMD5((token+text).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			new RuntimeException(e);
		}
		return null;
	}
	
	public static String getMD5(String text){
		try {
			return getMD5(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			new RuntimeException(e);
		}
		return null;
	}
	
	public static String getMD5(byte[] source) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			String s = null;
			return s.toString();
		}
	}
	
	public static String getIdCardMD5(String text){
		return getIdCardMD5(text,MD5_TEN);
	}
	
	public static String getIdCardMD5(String text,int num){
		String idCardMD5 = text;
		for(int i=0;i<num;i++){
			idCardMD5 = getMD5(idCardMD5);
		}
		return idCardMD5;
	}

}
