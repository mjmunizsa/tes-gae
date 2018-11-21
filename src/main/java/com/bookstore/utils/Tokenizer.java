package com.bookstore.utils;

import java.util.LinkedHashSet;

public class Tokenizer {
	
	public static String tokenize(String stringIn) {
		String [] words = stringIn.split(" ");
		LinkedHashSet<String> resultList = new LinkedHashSet<String>();
		
		for (String word : words) {
			for (int i=0;i<word.length();i++) {
				for (int j=i+1;j<=word.length();j++) {
					resultList.add(word.substring(i, j));
				}
			}
		}
		String result = "";
		for (String string : resultList) {
			result+=string + " ";
		}
		return result;
	}

}
