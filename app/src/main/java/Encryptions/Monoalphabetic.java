package Encryptions;

class Monoalphabetic {
	public Monoalphabetic(){}
	private static final String EngAlphabitics = "abcdefghijklmnopqrstuvwxyz";
	private static final String ArbAlphabitics = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
	

	public static String encrypt(String sequence,String plainText) throws InvalidInputException{
		if(!(validateArabicText(plainText) && validateArabSequence(sequence))  && !(validateEngText(plainText) && validateEngSequence(sequence)))
			throw new InvalidInputException();
		
		String cipherText="";
		for(char c : plainText.toCharArray()){
			for(int i =0;i<EngAlphabitics.length();i++){
				if(c==EngAlphabitics.charAt(i)){
					cipherText+=sequence.charAt(i);
				}
			}
		}
		return cipherText;
	}
	
	public static String decrypt(String sequence,String cipherText) throws InvalidInputException{
		if(!(validateArabicText(cipherText) && validateArabSequence(sequence))  && !(validateEngText(cipherText) && validateEngSequence(sequence)))
			throw new InvalidInputException();
		
		String plainText="";
		for(char c : cipherText.toCharArray()){
			for(int i =0;i<sequence.length();i++){
				if(c==sequence.charAt(i)){
					plainText+=EngAlphabitics.charAt(i);
				}
			}
		}
		return plainText;
	}
	
	private static boolean validateEngText(String text){
		for(char c :text.toCharArray()){
			if((int)c<97 && (int)c>122){
				return false;
			}
		}
		return true;
	}
	
	private static boolean validateArabicText(String text){
		for(char c :text.toCharArray()){
			if(!ArbAlphabitics.contains(c+"")){
				return false;
			}
		}
		return true;
	}
	
	private static boolean validateEngSequence(String sequence){
		//check for length
		if(sequence.length()!=26){
			return false;
		}
		
		//check for character replication
				for(int i =0 ; i <sequence.length();i++){
					for(int j=i+1;j<sequence.length();j++){
						if(sequence.charAt(i)==sequence.charAt(j)){
							return false;
						}
					}
				}
		
		//check for lowercase english alphabitics
		for(char c :sequence.toCharArray()){
			if((int)c<97 && (int)c>122){
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean validateArabSequence(String sequence){
		//check for length
		if(sequence.length()!=28){
			return false;
		}
		//check for character replication
				for(int i =0 ; i <sequence.length();i++){
					for(int j=i+1;j<sequence.length();j++){
						if(sequence.charAt(i)==sequence.charAt(j)){
							return false;
						}
					}
				}
				
		//check for lowercase english alphabitics
		for(char c :sequence.toCharArray()){
			if(!ArbAlphabitics.contains(c+"")){
				return false;
			}
		}
		
		return true;
	}
}