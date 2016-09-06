package com.anzusdev.turning.businesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.anzusdev.turning.util.CipherAndKey;

public class HandleKey {
	
	public static final char UNUSED_CHAR = '%';
	public static final int NUMBER_OF_ROTATIONS = 4;
	
	public HandleKey(){	}
	
	public ArrayList<Integer> generateKey(int n){
		ArrayList<Integer> grilleKey = new ArrayList<>();
		ArrayList<Integer> data = new ArrayList<>();
		for(int i = 0; i < n*n; i++)
			data.add(i);
		
		for(int i = 0; i <n*n; i+=NUMBER_OF_ROTATIONS){
			int startKey = (int) (Math.random()*data.size());
			grilleKey.add(data.get(startKey));
			for(int item : rotateItem(n, data.get(startKey)))
				data.remove(data.indexOf(item));
		}
		return grilleKey;
	}
	
	public HashMap<Integer, ArrayList<Integer> > generateKeyMap(int n){
		HashMap<Integer, ArrayList<Integer> > keyMap = new HashMap<>();
		ArrayList<Integer> data = new ArrayList<>();
		for(int i = 0; i < n*n; i++){
			data.add(i);
			if(i < NUMBER_OF_ROTATIONS)
				keyMap.put(i, new ArrayList<Integer>());
		}
		//int[] pru = {0,9,11,14};   //uncomment this to test with the example given in class 
		for(int i = 0; i <n*n; i+=NUMBER_OF_ROTATIONS){
			//ArrayList<Integer> items = rotateItem(n, data.get( data.indexOf(pru[i/4])));  //uncomment this to test with the example given in class
			int startKey = (int) (Math.random()*data.size()); //comment this to test with the example given in class
			ArrayList<Integer> items = rotateItem(n, data.get(startKey)); //comment this to test with the example given in class
			for(int j = 0; j < items.size(); j++ ){
				keyMap.get(j).add( data.remove( data.indexOf( items.get(j) ) ) );
			}
		}
		return orderKeys(keyMap, n);
	}
	
	public ArrayList<Integer> generateDecipher(ArrayList<Integer> grilleKey){
		int n = (int)Math.sqrt( grilleKey.size() * NUMBER_OF_ROTATIONS );
		HashMap<Integer, ArrayList<Integer>> keyMap = new HashMap<>();
		for(int i = 1; i < NUMBER_OF_ROTATIONS; i++)
			keyMap.put(i, new ArrayList<Integer>());
		
		keyMap.put(0, grilleKey);
		for(int i = 0; i < grilleKey.size(); i++){
			ArrayList<Integer> rotation = rotateItem(n, grilleKey.get(i) );
			for(int j = 1; j < NUMBER_OF_ROTATIONS; j++){
				keyMap.get(j).add( rotation.get(j) );
			}
		}
		
		keyMap = orderKeys(keyMap, n);
		ArrayList<Integer> decipher = new ArrayList<>();
		for(ArrayList<Integer> mapItem : keyMap.values())
			decipher.addAll(mapItem);
		
		return decipher;
	}
	
	public ArrayList<Integer> rotateItem(int n, int pos){
		ArrayList<Integer> rotations = new ArrayList<>();
		rotations.add(pos);
		
		int rotation1 = -1, rotation2 = -1, rotation3 = -1;
		for(int i = 0; i < n; i++){
			if(rotation1 >= 0 && rotation2 >= 0 && rotation3 >= 0)
				break;
			for(int j = 0; j < n; j++){
				if( (n - 1 - i) + j * n == pos )
					rotation1 = i*n+j;
				if( (n - 1 - i) * n + (n - 1 - j) == pos )
					rotation2 = i*n+j;
				if( i + (n - 1 - j) * n == pos )
					rotation3 = i*n+j;
				
				if(rotation1 >= 0 && rotation2 >= 0 && rotation3 >= 0)
					break;
			}
		}
		rotations.add(rotation1);
		rotations.add(rotation2);
		rotations.add(rotation3);
		return rotations;
	}
	
	public String decipher(ArrayList<Integer> grilleKey, String ciphertext){
		ArrayList<Integer> decipher = generateDecipher(grilleKey);
		String plaintext = "";
		for(int pos : decipher)
			plaintext += ciphertext.charAt(pos);
		return plaintext.replace('%', ' ').trim();
	}
	
	public int getN(String message){
		int n = (int)Math.ceil( Math.sqrt(message.length()) );
		return n % 2 == 0 ? n : n + 1;
	}
	
	public String getCipherText( String message ){
		int n = getN(message);
		message += addChars(n*n - message.length());
		HashMap<Integer, ArrayList<Integer>> keyMapOrdered = new HashMap<>();
		
		char[] cipherArray = new char[n*n];
		
		keyMapOrdered = generateKeyMap(n);
		
		for(int i = 0; i < NUMBER_OF_ROTATIONS; i++){
			for(int pos : keyMapOrdered.get(i)){
				cipherArray[pos] = message.charAt(0);
				message = message.substring(1);
			}
		}
		
		String output = "";
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++)
				output += cipherArray[i*n + j];
		}
		return output;
	}
	
	public CipherAndKey getCipherTextWithKey( String message ){
		int n = getN(message);
		message += addChars(n*n - message.length());
		HashMap<Integer, ArrayList<Integer>> keyMapOrdered = new HashMap<>();
		
		char[] cipherArray = new char[n*n];
		
		keyMapOrdered = generateKeyMap(n);
		
		for(int i = 0; i < NUMBER_OF_ROTATIONS; i++){
			for(int pos : keyMapOrdered.get(i)){
				cipherArray[pos] = message.charAt(0);
				message = message.substring(1);
			}
		}
		
		String cipherText = "";
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++)
				cipherText += cipherArray[i*n + j];
		}
		return new CipherAndKey(cipherText, keyMapOrdered.get(3), keyMapOrdered);
	}
	
	private HashMap<Integer, ArrayList<Integer> > orderKeys(HashMap<Integer, ArrayList<Integer> > keyMap, int n){
		HashMap<Integer, ArrayList<Integer> > newKeyMap = new HashMap<>();
		ArrayList<Integer> newKey1 = new ArrayList<>();
		ArrayList<Integer> newKey2 = new ArrayList<>();
		ArrayList<Integer> newKey3 = new ArrayList<>();
		for(int i = n - 1; i >= 0; i--){
			for(int j = n - 1; j >= 0; j--){
				int element = j * n + (n - 1 - i);
				int element2 = i * n + j;
				int element3 = i + (n - 1 - j) * n;
				if( keyMap.get(1).indexOf(element) != -1)
					newKey1.add(element);
				if( keyMap.get(2).indexOf(element2) != -1)
					newKey2.add(element2);
				if( keyMap.get(3).indexOf(element3) != -1)
					newKey3.add(element3);
			}
		}
		Collections.sort(keyMap.get(0));
		newKeyMap.put(0, newKey3);
		newKeyMap.put(1, newKey2);
		newKeyMap.put(2, newKey1);
		newKeyMap.put(3, keyMap.get(0));
		return newKeyMap;
	}
	
	private String addChars(int numberOfChars){
		String chars = ""; 
		for(int i = 0; i < numberOfChars; i++)
			chars += UNUSED_CHAR;
		return chars;
	}

}
