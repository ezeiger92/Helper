package com.chromaclypse.helper.guide;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PageRedirect implements Listener {
	
	private HashMap<String, Book> sections = new HashMap<>();
	
	public void registerSection(Section a) {
		sections.put(a.getSection(), a);
	}
	
	public Book get(String ident) {
		return sections.get(ident);
	}
	
	public void reset() {
		sections.clear();
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String[] parts = event.getMessage().split(" ", 2);
		
		if(parts.length > 1 && parts[0].toLowerCase().equals("/helper-guide-page")) {
			
			Book p = sections.get(parts[1].toLowerCase());
			
			if(p != null) {
				p.sendTo(event.getPlayer());
				event.setCancelled(true);
			}
		}
	}
}
