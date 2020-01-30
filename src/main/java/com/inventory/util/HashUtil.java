package com.inventory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class HashUtil {

	public static String secureHash(String key) {
		String secureKey=null;
		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-1");
			byte[] b=digest.digest(key.getBytes());
			secureKey=DatatypeConverter.printHexBinary(b).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return secureKey;
	}
}
