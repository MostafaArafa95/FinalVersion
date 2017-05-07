package Encryptions;

import java.util.ArrayList;
class PlayFair6x6 {
	
	private static final String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static char[][] matrix = new char[6][6];
	private static ArrayList<Character> matrixChars = new ArrayList<>();

	public static String encrypt(String keyword, String plainText) throws InvalidInputException{
		if(validateUserInput(keyword) && validateUserInput(plainText))
			throw new InvalidInputException();
			
		prepareMatrixChars(keyword);
		buildMatrix();
		String cipherText="";
		plainText = preparePlainText(plainText);
		for(int i =0 ; i< plainText.length()-1 ;i+=2 ){
			cipherText += encrypt2chars(plainText.charAt(i), plainText.charAt(i+1));
		}
		return cipherText;
	}
	
	public static String decrypt(String keyword, String cipherText) throws InvalidInputException{
		if(validateUserInput(keyword) && validateUserInput(cipherText))
			throw new InvalidInputException();
		
		prepareMatrixChars(keyword);
		buildMatrix();
		String plainText="";
		cipherText = preparePlainText(cipherText);
		for(int i =0 ; i< cipherText.length()-1 ;i+=2 ){
			plainText += decrypt2chars(cipherText.charAt(i), cipherText.charAt(i+1));
		}
		plainText += decrypt2chars(cipherText.charAt(cipherText.length()-2), cipherText.charAt(cipherText.length()-1));
		plainText = cleanOutputPlainText(plainText);
		return plainText;
	}
	
	private static void printMatrix(){
		System.out.println("The matrix:\n\n");
		for(int i = 0 ; i < 6 ; i++){
			for(int j = 0 ; j < 6 ; j++){
				System.out.print(matrix[i][j]+"  ");
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}
	
	private static boolean validateUserInput(String Text){
		int a2z =0;
		int z29 =0;
		//input are from a-z
		for(char c : Text.toCharArray()){
			if((int)c>=97 && (int)c<=122){
				a2z++;
			}
		}
		//input are from 0-9
		for(char c : Text.toCharArray()){
			if((int)c>=48 && (int)c<=57){
				z29++;
			}
		}
		if((a2z+z29) == Text.length()){
			return true;
		}
		return false;
	}
	
	private static void prepareMatrixChars(String keyword){
		//clear matrixChars in case it was used before
		if(matrixChars.size()>0)
		matrixChars.clear();
		//add keyword chars to matrixChars
		for(char c : keyword.toCharArray()){
			matrixChars.add(c);
		}
		//add allowedChars to matrixCharts
		for(char c: allowedChars.toCharArray()){
			matrixChars.add(c);
		}
		//cleaning matrixChars from duplicate chars
		for(int i = 0 ; i < matrixChars.size() ; i++){
			for(int j = i+1 ; j < matrixChars.size() ; j++){
				if(matrixChars.get(i).equals(matrixChars.get(j))){
					matrixChars.remove(j);
				}
			}
		}
	}
	
	private static void buildMatrix(){
		for(int i = 0 ; i < 6 ; i ++){
			for(int j = 0 ; j < 6 ; j++){
				matrix[i][j] = dequeueMatrixChars();
			}
		}
		printMatrix();
	}
	
	private static char dequeueMatrixChars(){
		char x = matrixChars.get(0);
		matrixChars.remove(0);
		return x;
	}
	
	private static int[] getCharXY(char c){
		int[] XY = new int[2];
		for(int i = 0 ; i < 6 ; i ++){
			for(int j = 0 ; j < 6 ; j++){
				if(matrix[i][j] == c){
					XY[0]=i;
					XY[1]=j;
					return XY;
				}
			}
		}
		//it will never reach this part
		return new int[2];
	}
	
	private static String preparePlainText(String plainText){
		String preparedText = "";
		//check if there are duplicated characters
		for(int i = 0 ; i < plainText.length() -1 ; i ++){
				if(plainText.charAt(i) == plainText.charAt(i+1)){
					preparedText += plainText.charAt(i)+""+'x';
				}else{
					preparedText += plainText.charAt(i);
				}
		}
		preparedText += plainText.charAt(plainText.length()-1);
		//if the prepared plan text length is not an even number add character 'x' to it
		if(preparedText.length()%2 != 0 ){
			preparedText+='x';
		}
		return preparedText;
	}
	
	private static String encrypt2chars(char char1 , char char2){
		int char1X,char1Y,char2X,char2Y;
		int[] XY1 = new int[2];
		int[] XY2 = new int[2];
		XY1 = getCharXY(char1);
		XY2 = getCharXY(char2);
		char1X = XY1[0];
		char1Y = XY1[1];
		char2X = XY2[0];
		char2Y = XY2[1];
		//if the two characters are at the same row
		if(char1X == char2X){
			char1Y = (char1Y+1)%6;
			char2Y = (char2Y+1)%6;
			char1 = matrix[char1X][char1Y];
			char2 = matrix[char2X][char2Y];
			return char1+""+char2;
		}
		//if the characters are at the same column 
		else if(char1Y == char2Y){
			char1X = (char1X+1)%6;
			char2X = (char2X+1)%6;
			char1 = matrix[char1X][char1Y];
			char2 = matrix[char2X][char2Y];
			return char1+""+char2;
		}
		//are not at the same row or column
		int yTemp;
		yTemp = char1Y;
		char1Y = char2Y;
		char2Y = yTemp;
		char1 = matrix[char1X][char1Y];
		char2 = matrix[char2X][char2Y];
		return char1+""+char2;
	}
	
	private static String decrypt2chars(char char1 , char char2){
		int char1X,char1Y,char2X,char2Y;
		int[] XY1 = new int[2];
		int[] XY2 = new int[2];
		XY1 = getCharXY(char1);
		XY2 = getCharXY(char2);
		char1X = XY1[0];
		char1Y = XY1[1];
		char2X = XY2[0];
		char2Y = XY2[1];
		//if the two characters are at the same row
		if(char1X == char2X){
			char1Y = (char1Y-1);
			char2Y = (char2Y-1);
			
			if(char1Y<0){
				char1Y+=6;
			}
			if(char2Y<0){
				char2Y+=6;
			}
			char1 = matrix[char1X][char1Y];
			char2 = matrix[char2X][char2Y];
			return char1+""+char2;
		}
		//if the characters are at the same column 
		else if(char1Y == char2Y){
			char1X = (char1X-1);
			char2X = (char2X-1);
			if(char1X<0){
				char1X+=6;
			}
			if(char2X<0){
				char2X+=6;
			}
			char1 = matrix[char1X][char1Y];
			char2 = matrix[char2X][char2Y];
			return char1+""+char2;
		}
		//are not at the same row or column
		int yTemp;
		yTemp = char1Y;
		char1Y = char2Y;
		char2Y = yTemp;
		char1 = matrix[char1X][char1Y];
		char2 = matrix[char2X][char2Y];
		return char1+""+char2;
	}
	
	private static String cleanOutputPlainText(String plainText){
		String cleanString ="";
		for(int i = 0 ; i < plainText.length()-2 ; i++){
			if(plainText.charAt(i+1)=='x'){
				if(plainText.charAt(i)== plainText.charAt(i+2)){
					cleanString+=plainText.charAt(i)+""+plainText.charAt(i+2);
					i=i+2;
				}else{
					cleanString+=plainText.charAt(i);
				}
			}else{
				cleanString+=plainText.charAt(i);
			}
		}
		return cleanString;
	}
	
}
