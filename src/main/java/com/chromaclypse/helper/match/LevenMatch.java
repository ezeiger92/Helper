package com.chromaclypse.helper.match;

import java.util.Comparator;

public class LevenMatch {
	public static Comparator<LevenMatch> comparator() {
		return (lhs, rhs) -> lhs.compareTo(rhs);
	}
	
	private String str;
	private int levenDist;
	
	public LevenMatch(String str, int levenDist) {
		this.str = str;
		this.levenDist = levenDist;
	}
	
	public String getString() {
		return str;
	}
	
	public int getDistance() {
		return levenDist;
	}
	
	public int compareTo(LevenMatch rhs) {
		int result = Integer.compare(levenDist, rhs.levenDist);
		if(result == 0)
			result = str.compareToIgnoreCase(rhs.str);
		return result;
	}
	
	@Override
	public String toString() {
		return str + "(" + String.valueOf(levenDist) + ")";
	}
}
