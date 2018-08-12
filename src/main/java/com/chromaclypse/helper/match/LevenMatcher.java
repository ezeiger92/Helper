package com.chromaclypse.helper.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Function;

import com.chromaclypse.helper.Util;

public class LevenMatcher {
	private String source;
	private boolean stopOnExact;
	private Collection<String> potentials;
	private Comparator<? super LevenMatch> sorting;
	private Predicate<? super LevenMatch> filter;

	public LevenMatcher() {
		source = null;
		stopOnExact = false;
		potentials = new ArrayList<>();
		sorting = LevenMatch.comparator();
		filter = null;
	}
	
	public LevenMatcher matching(String source) {
		this.source = source;
		return this;
	}
	
	public LevenMatcher searching(Collection<String> potentials) {
		return searching(potentials, s-> s);
	}
	
	public <T> LevenMatcher searching(Collection<T> potentials, Function<T, String> transform) {
		for(T object : potentials) {
			String potential = transform.apply(object);
			if(potential != null)
				this.potentials.add(potential);
		}

		return this;
	}
	
	public LevenMatcher sorting(Comparator<? super LevenMatch> comp) {
		sorting = comp;
		return this;
	}
	
	public LevenMatcher filtering(Predicate<? super LevenMatch> filter) {
		this.filter = filter;
		return this;
	}
	
	public LevenMatcher stopOnExact(boolean state) {
		stopOnExact = state;
		return this;
	}
	
	public LevenResult get() {
		String lower = source.toLowerCase();
		Map<String, LevenMatch> matchMap = new HashMap<String, LevenMatch>(potentials.size());

		for(String potential : potentials) {
			int distance = Util.levenshteinDistance(potential.toLowerCase(), lower);
			LevenMatch match = new LevenMatch(potential, distance);
			if(stopOnExact && distance == 0)
				return new LevenResult(Arrays.asList(match), true);
			
			matchMap.putIfAbsent(potential.toLowerCase(), match);
		}

		LevenResult results = new LevenResult(matchMap.values(), false);
		List<LevenMatch> matches = results.getMatches();
		if(filter != null)  matches.removeIf(filter);
		if(sorting != null) matches.sort(sorting);
		return results;
	}
}
