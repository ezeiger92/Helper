package com.chromaclypse.helper.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LevenResult {
	private List<LevenMatch> matches;
	private boolean exact;
	
	public LevenResult() {
		this.matches = new ArrayList<>();
		this.exact = false;
	}
	
	public LevenResult(Collection<LevenMatch> matches, boolean exact) {
		setMatches(matches);
		this.exact = exact;
	}
	
	public List<LevenMatch> getMatches() {
		return matches;
	}
	
	public boolean isExact() {
		return exact;
	}
	
	public void setMatches(Collection<LevenMatch> matches) {
		this.matches = new ArrayList<>(matches.size());
		this.matches.addAll(matches);
	}
	
	public void setExact(boolean exact) {
		this.exact = exact;
	}
}
