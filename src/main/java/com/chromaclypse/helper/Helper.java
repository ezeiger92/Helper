package com.chromaclypse.helper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Helper extends JavaPlugin {
	
	HelperConfig config = new HelperConfig();
	CloseCommand closeCommand = new CloseCommand(config);
	

	@Override
	public void onEnable() {
		reload();
		
		getCommand("helper").setExecutor(this);
		getCommand("help").setExecutor(this);
	}
	
	public void reload() {
		HandlerList.unregisterAll(closeCommand);
		
		config.init(this);
		
		if(!config.enabled)
			return;
		
		getServer().getPluginManager().registerEvents(closeCommand, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage("[Helper] Version " + getDescription().getVersion());
			sender.sendMessage("[Helper] /helper reload to reload");
			return true;
		}
		else if(args[0].equalsIgnoreCase("reload")) {
			boolean hasPermission = sender.hasPermission("helper.reload");
			
			if(!hasPermission)
				return false;
			
			sender.sendMessage("[Helper] Reloading config");
			reload();
			return true;
		}
		else if(args[0].equalsIgnoreCase("search")) {
			sender.sendMessage("[Helper] Nothing to see here yet");
			return true;
		}
		
		sender.sendMessage("[Helper] Unknown arg \"" + args[0] + "\"");
		return true;
	}
}
