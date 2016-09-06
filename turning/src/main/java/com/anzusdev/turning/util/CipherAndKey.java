package com.anzusdev.turning.util;

import java.util.ArrayList;
import java.util.HashMap;

public class CipherAndKey {
	
	private String cipherText;
	private ArrayList<Integer> grilleKey;
	private HashMap<Integer, ArrayList<Integer>> keyMap;

	public CipherAndKey(String cipherText, ArrayList<Integer> grilleKey, HashMap<Integer, ArrayList<Integer>> keyMap ){
		this.cipherText = cipherText;
		this.grilleKey = grilleKey;
		this.keyMap = keyMap;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	public ArrayList<Integer> getKey() {
		return grilleKey;
	}

	public void setKey(ArrayList<Integer> grilleKey) {
		this.grilleKey = grilleKey;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(HashMap<Integer, ArrayList<Integer>> keyMap) {
		this.keyMap = keyMap;
	}

}
