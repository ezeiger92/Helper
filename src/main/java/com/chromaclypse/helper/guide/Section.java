package com.chromaclypse.helper.guide;

import org.bukkit.entity.Player;

import com.chromaclypse.api.json.JsonBlob;
import com.chromaclypse.api.json.Prop;
import com.chromaclypse.api.messages.Messager;
import com.chromaclypse.api.messages.Text;
import com.chromaclypse.helper.guide.Guidebook.SerialSection;

public class Section implements Book {
	private JsonBlob page;
	private JsonBlob[] extra;
	private String section;
	
	public Section(SerialSection parts) {
		section = parts.category;
		
		int length = parts.pages.size() - 1;
		
		page = addHeader(JsonBlob.fromLegacy(Text.format().colorize(parts.pages.get(0))));
		extra = new JsonBlob[length];
		
		if(length > 0) {
			for(int i = 0; i < length; ++i) {
				extra[i] = addHeader(JsonBlob.fromLegacy(Text.format().colorize(parts.pages.get(i + 1))));
			}
		}
	}
	
	public String getSection() {
		return section;
	}
	
	private JsonBlob addHeader(JsonBlob data) {
		return new JsonBlob()
				.add("")
				.add("<--", Prop.CLICK_RUN("/helper-guide-page home"), Prop.HOVER_TEXT("Back"), Prop.BOLD)
				.add("\n")
				.add(data);
	}
	
	@Override
	public void sendTo(Player player) {
		Messager.sendBook(player, page, extra);
	}
}
