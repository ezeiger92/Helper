package com.chromaclypse.helper.guide;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PageRedirect implements Listener {
	
	private HashMap<String, Page> pages = new HashMap<>();
	
	public void registerSection(Section a) {
		
		for(Page p : a.getPages()) {
			pages.put(p.toString().toLowerCase(), p);
		}
	}
	
	public Page get(String ident) {
		return pages.get(ident);
	}
	
	public void reset() {
		pages.clear();
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String[] parts = event.getMessage().split(" ", 2);
		
		if(parts.length > 1 && parts[0].toLowerCase().equals("/helper-guide-page")) {
			
			Page p = pages.get(parts[1].toLowerCase());
			
			if(p != null) {
				p.sendTo(event.getPlayer());
				event.setCancelled(true);
			}
		}
	}
}
