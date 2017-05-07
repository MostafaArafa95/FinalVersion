package Encryptions;

class Caesar {
	private static final String EngAlphabitics = "abcdefghijklmnopqrstuvwxyz";
	private static final String ArbAlphabitics = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
	private static boolean validateEngInput(String text){
		for(char c : text.toCharArray()){
			if(!EngAlphabitics.contains(c+"")){
				return false;
			}
		}
		return true;
	}
	private static boolean validateArbInput(String text){
		for(char c : text.toCharArray()){
			if(!ArbAlphabitics.contains(c+"")){
				return false;
			}
		}
		return true;
	}
	public static String encrypt(String plainText, int key) throws InvalidInputException{
		String cipherText ="";
		String lang = "";
		if(validateEngInput(plainText)){
			lang = EngAlphabitics;
		}else if(validateArbInput(plainText)){
			lang = ArbAlphabitics;
		}else
			throw new InvalidInputException();
		for(char c : plainText.toCharArray()){
			int charIndex = (lang.indexOf(c)+key)%lang.length();
			cipherText+= lang.charAt(charIndex);
		}
		return cipherText;
	}
	public static String decrypt(String cipherText, int key) throws InvalidInputException{
		String plainText ="";
		String lang = "";
		if(validateEngInput(cipherText)){
			lang = EngAlphabitics;
		}else if(validateArbInput(cipherText)){
			lang = ArbAlphabitics;
		}else
			throw new InvalidInputException();
		for(char c : cipherText.toCharArray()){
			int charIndex = (lang.indexOf(c)-key);
			while(charIndex<0)
				charIndex+=lang.length();
			plainText+= lang.charAt(charIndex);
		}
		return plainText;
	}
}
