package com.chromaclypse.helper.guide;

import java.util.List;

import com.chromaclypse.api.Defaults;
import com.chromaclypse.api.config.ConfigObject;
import com.chromaclypse.api.config.Section;

@Section(path="guide.yml")
public class Guidebook extends ConfigObject {

	public List<SerialSection> sections = Defaults.emptyList();
	public static class SerialSection {
		public String category = "category";
		public List<String> pages = Defaults.emptyList();
	}
}
