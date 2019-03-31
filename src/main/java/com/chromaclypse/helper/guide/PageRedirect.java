package com.chromaclypse.helper.guide;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chromaclypse.api.Defaults;
import com.chromaclypse.api.messages.Text;

public class PageRedirect implements CommandExecutor {
	
	private HashMap<String, Section> sections = new HashMap<>();
	private Map<String, String> aliases = Defaults.emptyMap();
	
	public void registerSection(Section a) {
		sections.put(a.getSection(), a);
	}
	
	public void setAliases(Map<String, String> aliases) {
		this.aliases = aliases;
	}
	
	public void reset() {
		sections.clear();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			String topic;
			
			if(args.length > 0) {
				topic = args[args.length -1].toLowerCase();
			}
			else {
				topic = "index";
			}
			
			Section p = sections.get(topic);
			
			if(p == null) {
				// missing alias returns null, which is safe for use in HashMap.get
				p = sections.get(aliases.get(topic));
			}
			
			if(p != null) {
				p.sendTo((Player) sender);
			}
			else {
				sender.sendMessage(Text.format().colorize("&cNo guide found!"));
			}
		}
		else {
			sender.sendMessage(Text.format().colorize("&cThis command only works for players"));
		}
		
		return true;
	}
}
