package com.chromaclypse.helper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.chromaclypse.helper.guide.Guidebook;
import com.chromaclypse.helper.guide.Guidebook.SerialSection;
import com.chromaclypse.helper.guide.PageRedirect;
import com.chromaclypse.helper.guide.Section;

public class Helper extends JavaPlugin {
	
	HelperConfig config = new HelperConfig();
	Guidebook guide = new Guidebook();
	CloseCommand closeCommand = new CloseCommand(config);
	PageRedirect redirect = new PageRedirect();
	

	@Override
	public void onEnable() {
		reload();
		
		getCommand("helper").setExecutor(this);
		getCommand("guide").setExecutor(redirect);
	}
	
	@Override
	public void onDisable() {
	}
	
	public void reload() {
		HandlerList.unregisterAll((Plugin) this);
		
		redirect.reset();
		
		config.init(this);
		guide.init(this);
		
		for(SerialSection s : guide.sections) {
			redirect.registerSection(new Section(s));
		}
		
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
