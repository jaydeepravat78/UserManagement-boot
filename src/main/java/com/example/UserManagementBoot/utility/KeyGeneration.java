package com.example.UserManagementBoot.utility;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


public class KeyGeneration {
	private static SecretKeySpec secretKey;
	private static final String secret = "AbDeV@aliean123";
	private static final Logger log = Logger.getLogger(KeyGeneration.class.getClass());

	public static void setKey(final String myKey) {
		MessageDigest sha;
		try {
			byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		}
	}

	public static String encrypt(String strToEncrypt) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
}
