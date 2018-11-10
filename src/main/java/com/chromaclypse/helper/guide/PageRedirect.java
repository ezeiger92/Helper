package com.chromaclypse.helper.guide;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.chromaclypse.api.messages.Text;

public class PageRedirect implements CommandExecutor {
	
	private HashMap<String, Section> sections = new HashMap<>();
	
	public void registerSection(Section a) {
		sections.put(a.getSection(), a);
	}
	
	public void reset() {
		sections.clear();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			String topic;
			
			if(args.length > 0) {
				topic = String.join(" ", args).toLowerCase();
			}
			else {
				topic = "index";
			}
			
			Section p = sections.get(topic);
			
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
