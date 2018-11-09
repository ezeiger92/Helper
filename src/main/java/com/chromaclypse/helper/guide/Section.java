package com.chromaclypse.helper.guide;

import java.util.Arrays;
import java.util.List;

import com.chromaclypse.api.Log;
import com.chromaclypse.api.json.JsonBlob;
import com.chromaclypse.api.json.Prop;
import com.chromaclypse.api.messages.Text;
import com.chromaclypse.helper.guide.Guidebook.SerialSection;

public class Section {
	private Page[] pages;
	private String section;
	
	public Section(SerialSection parts) {
		Log.info(parts.category);
		Log.info(parts.pages.toString());
		section = parts.category;
		
		int length = parts.pages.size();
		
		pages = new Page[length];
		
		for(int i = 0; i < length; ++i) {
			pages[i] = new Page(addHeader(JsonBlob.fromLegacy(Text.format().colorize(parts.pages.get(i))), i, length), section + "_" + i);
		}
	}
	
	public List<Page> getPages() {
		return Arrays.asList(pages);
	}
	
	private JsonBlob addHeader(JsonBlob data, int index, int length) {
		JsonBlob result = new JsonBlob();
		result.add("");
		result.add("<--", Prop.CLICK_RUN("/helper-guide-page home_0"), Prop.HOVER_TEXT("Back"), Prop.BOLD).add("\n");
		
		if(index > 0) {
			result.add("<", Prop.CLICK_RUN("/helper-guide-page " + section + "_" + (index - 1)), Prop.HOVER_TEXT("Page " + index));
		}
		else {
			result.add("<", Prop.GRAY);
		}
		
		result.add("                          ");
		
		if(index < length - 1) {
			result.add(">", Prop.CLICK_RUN("/helper-guide-page " + section + "_" + (index + 1)), Prop.HOVER_TEXT("Page " + (index + 2)));
		}
		else {
			result.add(">", Prop.GRAY);
		}
		
		Log.info(result.toString());
		
		return result.add("\n").add(data);
	}
}
