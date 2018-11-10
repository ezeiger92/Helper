package com.chromaclypse.helper.guide;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import com.chromaclypse.api.json.JsonBlob;
import com.chromaclypse.api.json.Prop;
import com.chromaclypse.api.messages.Messager;
import com.chromaclypse.api.messages.Text;
import com.chromaclypse.helper.guide.Guidebook.SerialSection;

public class Section {
	private JsonBlob page;
	private JsonBlob[] extra;
	private String section;
	
	public Section(SerialSection parts) {
		section = parts.category;
		
		int length = parts.pages.size() - 1;
		
		page = preprocess(JsonBlob.fromLegacy(Text.format().colorize(parts.pages.get(0))));
		extra = new JsonBlob[length];
		
		if(length > 0) {
			for(int i = 0; i < length; ++i) {
				extra[i] = preprocess(JsonBlob.fromLegacy(Text.format().colorize(parts.pages.get(i + 1))));
			}
		}
	}
	
	public String getSection() {
		return section;
	}
	
	private JsonBlob preprocess(JsonBlob data) {
		
		String json = data.toString().replaceAll(
				"\\[\\[([^|]+)\\|([^]]+)\\]\\]", "\"},{\"text\":\"$1\",\"clickEvent\":{"
				+ "\"action\":\"run_command\",\"value\":\"/helper:guide $1\""
				+ "},\"hoverEvent\":{"
				+ "\"action\":\"show_text\",\"value\":\"$2\""
				+ "},\"color\":\"dark_aqua\"},{\"text\":\"");
		
		JsonBlob blob = new JsonBlob();
		StringBuilder builder = new StringBuilder(json);
		
		try {
			Field f = JsonBlob.class.getDeclaredField("builder");
			boolean accessible = f.isAccessible();
			f.setAccessible(true);
			f.set(blob, builder);
			f.setAccessible(accessible);
		}
		catch(Exception e) {
			e.printStackTrace();
			blob = data;
		}
		
		JsonBlob result = new JsonBlob().add("").add("   " + Text.format().niceName(section) + "\n", Prop.DARK_BLUE);
		
		if(!section.equals("index")) {
			result.add("<--", Prop.CLICK_RUN("/helper:guide"), Prop.HOVER_TEXT("Back to index"), Prop.BOLD);
		}
		
		return result.add("\n").add(blob);
	}
	
	public void sendTo(Player player) {
		Messager.sendBook(player, page, extra);
	}
}
