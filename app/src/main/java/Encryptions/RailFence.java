package Encryptions;

class RailFence {
	
	public static String encrypt(int depth, String plainText) {
		int numOfColumns = Math
				.round(((float) plainText.length() / (float) depth));
		char[][] railFence = new char[depth][numOfColumns];
		editRailFence(railFence, depth, numOfColumns);
		int index = 0;
		for (int i = 0; i < numOfColumns; i++) {
			for (int j = 0; j < depth; j++) {
				if (index < plainText.length()) {
					railFence[j][i] = plainText.charAt(index);
					index++;
				}
			}
		}
		return returnRailFence(railFence, depth, numOfColumns);
	}

	public static String decrypt(int depth, String cipherText) {
		int numOfColumns = cipherText.length() / depth;
		char[][] railFence = new char[depth][numOfColumns];
		int index = 0;
		String plainText = "";
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < numOfColumns; j++) {
				railFence[i][j] = cipherText.charAt(index);
				index++;
			}
		}
		//printRailFence(railFence, depth, numOfColumns);
		for (int i = 0; i < numOfColumns; i++) {
			for (int j = 0; j < depth; j++) {
					plainText += railFence[j][i];
			}
		}
		//printRailFence(railFence, depth, numOfColumns);
		return cleanPlainText(plainText);
	}
	
	private static String returnRailFence(char[][] railFence, int depth , int columns){
		String railString = "";
		for(int i = 0 ; i < depth ; i++){
			for(int j = 0 ; j < columns ; j ++){
				railString += railFence[i][j];
			}
		}
		return railString;
	}

	private static String cleanPlainText(String plainText) {
		String returnString = "";
		for (char c : plainText.toCharArray()) {
			if (c != '-') {
				returnString += c;
			}
		}
		return returnString;
	}

	private static void editRailFence(char[][] railFence, int depth, int columns) {
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < columns; j++) {
				railFence[i][j] = '-';
			}
		}
	}
}