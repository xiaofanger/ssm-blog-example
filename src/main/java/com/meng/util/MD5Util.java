package com.meng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 文字和文件加密工具
 * @author Administrator
 *
 */
public class MD5Util {
	
	protected static char hex[] ={'0', '1', '2', '3', '4', '5', '6'
			, '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	protected static MessageDigest messageDigest = null;
	
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得文件 MD5 钥匙
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		FileInputStream filein = new FileInputStream(file);
		FileChannel ch = filein.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messageDigest.update(byteBuffer);
		return bufferToHex(messageDigest.digest());
		
	}
	
	/**
	 * 获得字符串MD5钥匙
	 * @param str
	 * @return
	 */
	public static String getMD5String(String str) {
		return getMD5String(str.getBytes());
	}
	
	public static String getMD5String(byte[] bytes) {
		messageDigest.update(bytes);
		return bufferToHex(messageDigest.digest());
	}
	
	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}
	
	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuffer stringBuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int i = m; i < k; i++) {
			appendHexPair(bytes[i], stringBuffer);
		}
		return stringBuffer.toString();
	}
	
	private static void appendHexPair(byte bt, StringBuffer stringBuffer) {
		char c0 = hex[(bt & 0x0f) >> 4];
		char c1 = hex[bt & 0xf];
		stringBuffer.append(c0);
		stringBuffer.append(c1);
	}
}
