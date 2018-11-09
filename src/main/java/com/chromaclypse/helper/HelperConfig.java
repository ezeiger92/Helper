package com.chromaclypse.helper;

import java.util.List;

import com.chromaclypse.api.Defaults;
import com.chromaclypse.api.config.ConfigObject;

public class HelperConfig extends ConfigObject {
	public boolean enabled;
	public Suggestions suggest = new Suggestions();
	public static class Suggestions {
		public int maxRange = 6;
		public int maxResults = 5;
		public boolean permFilter = false;
		public boolean includesAliases = false;
		public List<String> unlistedCommands = Defaults
				.list("Not_a_real_command", "but_the_server_acts", "like_I_am");
	}
	
	public int resultsPerPage = 5;

	public HelpTopic help = new HelpTopic();
	public static class HelpTopic {
		public String name = "name";
		public String brief = "dummy brief";
		public String permission = "*";
		public String noPermMessage = "Unknown Command";
		public String description = "Dummy desc";
		public List<HelpTopic> topics = Defaults.emptyList();
	}
}
