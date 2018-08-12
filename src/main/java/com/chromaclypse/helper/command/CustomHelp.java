package com.chromaclypse.helper.command;

import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.chromaclypse.helper.HelperConfig;
import com.chromaclypse.helper.HelperConfig.HelpTopic;

public class CustomHelp implements CommandExecutor {
	
	private HelperConfig config;
	
	public CustomHelp(HelperConfig config) {
		this.config = config;
	}
	
	private static boolean isAnyPerm(String permissionString) {
		return permissionString == null || permissionString.length() == 0 || "*".equals(permissionString);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int page = 0;
		int len = args.length;
		HelpTopic topic = config.help;
		
		for(int i = 0; i < len; ++i) {
			String arg = args[i].toLowerCase(Locale.getDefault());
			
			HelpTopic subtopic = null;
			for(HelpTopic t : topic.topics) {
				if(t.name.toLowerCase(Locale.getDefault()).equals(arg) && (isAnyPerm(t.permission) || sender.hasPermission(t.permission))) {
					subtopic = t;
					
					break;
				}
			}
			
			if(subtopic == null) {
				try {
					page = Integer.parseInt(arg);
				}
				catch(NumberFormatException e) {
					sender.sendMessage("&cNo help found!");
					return true;
				}
				
				break;
			}
			else
				topic = subtopic;
		}
		
		sender.sendMessage("=== " + topic.name + " ===");
		
		if(page <= 0)
			sender.sendMessage("  " + topic.brief);
		
		for(int i = config.resultsPerPage * page, end = i + config.resultsPerPage;
				i < end; ++i) {
			if(topic.topics.size() <= i)
				break;
			
			sender.sendMessage("- " + topic.topics.get(i).name);
		}
		
		return true;
	}
}
