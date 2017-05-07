package Encryptions;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptographyManager {

	/**
	 * @param key
	 * @param plainText
	 * @param algorithm
	 * @return Cipher Text
	 * @throws InvalidInputException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * <br>
	 * Supported algorithms ("PlayFair6x6, RailFence, RowTransposition, Vigenere, Monoalphabetic, AES128, Caesar").
	 */
	public static String encrypt(String key, String plainText, String algorithm) throws InvalidInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		switch(algorithm.toLowerCase()){
			case "playfair":
				return PlayFair6x6.encrypt(key, plainText);
			case "railfence":
				return RailFence.encrypt(Integer.parseInt(key), plainText);
			case "rowtransposition":
				return RowTransposition.encrypt(key, plainText);
			case "vigenere":
				return Vigenere.encrypt(key, plainText);
			case "monoalphabetic":
				return Monoalphabetic.encrypt(key, plainText);
			case "aes":
				AES.encrypt(key, plainText);
			case "caesar":
				return Caesar.encrypt(plainText, Integer.parseInt(key));
		}
		return null;
	}
	
	/**
	 * @param key
	 * @param cipherText
	 * @param algorithm name
	 * @return Plain Text
	 * @throws InvalidInputException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * <br>
	 * Supported algorithms ("PlayFair6x6, RailFence, RowTransposition, Vigenere, Monoalphabetic, AES128, Caesar").
	 */
	public static String decrypt(String key, String cipherText, String algorithm) throws InvalidInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
			switch(algorithm.toLowerCase()){
			case "playfair":
				return PlayFair6x6.decrypt(key, cipherText);
			case "railfence":
				return RailFence.decrypt(Integer.parseInt(key), cipherText);
			case "rowtransposition":
				return RowTransposition.decrypt(key, cipherText);
			case "vigenere":
				return Vigenere.decrypt(key, cipherText);
			case "monoalphabetic":
				return Monoalphabetic.decrypt(key, cipherText);
			case "aes":
				AES.decrypt(key, cipherText);
			case "caesar":
				return Caesar.decrypt(cipherText, Integer.parseInt(key));
		}
		return null;
	}
}
