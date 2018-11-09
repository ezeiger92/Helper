package com.chromaclypse.helper.guide;

import org.bukkit.entity.Player;

import com.chromaclypse.api.json.JsonBlob;
import com.chromaclypse.api.messages.Messager;

public class Page {
	private JsonBlob data;
	private String identifier;
	
	Page(JsonBlob data, String identifier) {
		this.data = data;
		this.identifier = identifier;
	}
	
	@Override
	public int hashCode() {
		return identifier.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Page) {
			return identifier.equals(((Page)o).identifier);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	public void sendTo(Player player) {
		Messager.sendBook(player, data);
	}
}
