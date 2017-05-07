package Encryptions;

class RowTransposition {
	
	public static String decrypt(String sequence, String cipherText) throws InvalidInputException{
		if(sequence.length() > cipherText.length())
			throw new InvalidInputException();
		
		int columns = sequence.length();
		int rows = cipherText.length() / columns;
		char[][] matrix = new char[rows][columns];
		char[][] decMatrix = new char[rows][columns];
		buildMatrix(columns, rows, matrix, cipherText);
		int colIndex = 0;
		for(char c : sequence.toCharArray()){
			int encCol = Integer.parseInt(c+"") -1;
			for(int i = 0 ; i < rows ; i ++ ){
				decMatrix[i][encCol] = matrix[i][colIndex];
			}
			colIndex+=1;
		}
		return matrixToString(columns, rows, decMatrix);
	}
	
	public static String encrypt(String sequence, String plainText) throws InvalidInputException{
		if(sequence.length() > plainText.length())
			throw new InvalidInputException();
		
		int columns = sequence.length();
		int rows = (int)Math.round((double)plainText.length() / (double)columns);
		char[][] matrix = new char[rows][columns];
		char[][] encMatrix = new char[rows][columns];
		editRailFence(matrix, rows, columns);
		buildMatrix(columns, rows, matrix, plainText);
		int colIndex = 0;
		for(char c : sequence.toCharArray()){
			int encCol = Integer.parseInt(c+"") -1;
			for(int i = 0 ; i < rows ; i ++ ){
				encMatrix[i][colIndex] = matrix[i][encCol];
			}
			colIndex+=1;
		}
		return cleanHithen(matrixToString(columns, rows, encMatrix));
	}
	
	private static String matrixToString(int columns, int rows, char[][] matrix){
		String string = "";
		for(int i = 0 ; i < rows ; i ++){
			for(int j = 0 ; j < columns ; j++){
				string += matrix[i][j];
			}
		}
		return string;
	}
	
	private static void buildMatrix(int columns, int rows, char[][] matrix, String text){
		int index = 0; 
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (index < text.length()) {
					matrix[i][j] = text.charAt(index);
					index++;
				}
			}
		}
	}
	
	private static String cleanHithen(String Text){
		String plainText = "";
		for(char c : Text.toCharArray()){
			if(c!='-'){
				plainText+=c;
			}
		}
		return plainText;
	}
	
	private static void editRailFence(char[][] matrix, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix[i][j] = '-';
			}
		}
	}
}
