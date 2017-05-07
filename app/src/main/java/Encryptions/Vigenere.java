package Encryptions;

class Vigenere {
	private static final String ArbAlphabitics = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
	
	public static String encrypt(String keyword, String plainText) throws InvalidInputException{
		if(validateEnglishInput(keyword) && validateEnglishInput(plainText)){
			return encryptEnglish(keyword, plainText);
		}else if(validateArabicInput(keyword)&& validateArabicInput(plainText)){
			return encryptArabic(keyword, plainText);
		}else
			throw new InvalidInputException();
	}

	public static String decrypt(String keyword, String cipherText) throws InvalidInputException{
		if(validateEnglishInput(keyword) && validateEnglishInput(cipherText)){
			return decryptEnglish(keyword, cipherText);
		}else if(validateArabicInput(keyword)&& validateArabicInput(cipherText)){
			return decryptArabic(keyword, cipherText);
		}else
			throw new InvalidInputException();
	}

	private static boolean validateEnglishInput(String input){
		for(char c : input.toCharArray()){
			if((int)c < 97 || (int)c > 122){
				return false;
			}
		}
		return true;
	}
	
	private static boolean validateArabicInput(String input){
		for(char c : input.toCharArray()){
			if(!ArbAlphabitics.contains(c+"")){
				return false;
			}
		}
		return true;
	}
	
	private static String encryptArabic(String keyword, String plainText){
		String cipherText = "";
		keyword = keyStream(keyword, plainText);
		for(int i = 0 ; i < plainText.length() ; i++){
			int encCharIndex = ( arabicCharValue(plainText.charAt(i)) + arabicCharValue(keyword.charAt(i)))-1;
			encCharIndex = encCharIndex%28;
			cipherText += ArbAlphabitics.charAt(encCharIndex);
		}
		return cipherText;
	}

	private static String decryptArabic(String keyword, String cipherText){
		String plainText = "";
		keyword = keyStream(keyword, cipherText);
		for(int i = 0 ; i < cipherText.length() ; i++){
			int encDecIndex = ( arabicCharValue(cipherText.charAt(i)) - arabicCharValue(keyword.charAt(i)));
			encDecIndex--;
			while(encDecIndex < 0){
				encDecIndex+=28;
			}
			plainText += ArbAlphabitics.charAt(encDecIndex);
		}
		return plainText;
	}
	
	private static String encryptEnglish(String keyword, String plainText){
		String cipherText = "";
		keyword = keyStream(keyword, plainText);
		for(int i = 0 ; i < plainText.length() ; i++){
			cipherText += (char)(( ( charIndex(plainText.charAt(i))+charIndex(keyword.charAt(i)) ) % 26 )+97);
		}
		return cipherText;
	}
	
	private static String decryptEnglish(String keyword, String cipherText){
		String plainText = "";
		keyword = keyStream(keyword, cipherText);
		for(int i = 0 ; i < cipherText.length() ; i++){
			int asciiValue = charIndex(cipherText.charAt(i))-charIndex(keyword.charAt(i));
			while(asciiValue < 0){
				asciiValue+=26;
			}
			plainText+=(char)(asciiValue+97);
		}
		return plainText;
	}
	
	private static String keyStream(String keyword, String userInput){
		String generatedKeyword = "";
		for(int i =  0 ; i < userInput.length() ; i++){
			generatedKeyword += keyword.charAt(i % keyword.length());
		}
		return generatedKeyword;
	}
	
	private static int charIndex(char c){
		int cIndex = (int) c;
		cIndex-=97;
		return cIndex;
	}
	
	private static int arabicCharValue(char c){
		for(int i = 0 ; i < ArbAlphabitics.length() ; i++)
			if(c == ArbAlphabitics.charAt(i))
				return i+1;
		return 0;
	}
}
