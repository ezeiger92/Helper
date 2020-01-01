package com.chromaclypse.helper;

import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.chromaclypse.api.command.CommandBase;
import com.chromaclypse.api.command.Context;
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
		
		TabExecutor ast = new CommandBase()
				.calls(this::helpCommand)
				.with().arg("reload").calls(this::reloadCommand)
				.with().arg("version").calls(CommandBase::pluginVersion)
				.with().arg("search").calls(this::searchCommand)
				.getCommand();
		
		getCommand("assistant").setExecutor(ast);
		getCommand("assistant").setTabCompleter(ast);
		
		getCommand("guide").setExecutor(redirect);
	}
	
	@Override
	public void onDisable() {
	}
	
	public void reload() {
		HandlerList.unregisterAll((Plugin) this);
		
		String baseCommand = getDescription().getName() + ":guide";
		
		redirect.reset();
		
		config.init(this);
		guide.init(this);
		
		for(SerialSection s : guide.sections) {
			redirect.registerSection(new Section(s, baseCommand));
		}
		
		redirect.setAliases(guide.aliases);
		
		if(!config.enabled)
			return;

		getServer().getPluginManager().registerEvents(closeCommand, this);
	}
	
	public boolean helpCommand(Context context) {
		if(context.Args().length > 0) {
			context.Sender().sendMessage("[Assistant] Unknown arg \"" + context.GetArg(0) + "\"");
		}
		
		return CommandBase.pluginVersion(context);
	}
	
	public boolean reloadCommand(Context context) {
		if(context.Sender().hasPermission("helper.reload")) {
			context.Sender().sendMessage("[Assistant] Reloading config");
			reload();
			return true;
		}
		
		return false;
	}
	
	public boolean searchCommand(Context context) {
		context.Sender().sendMessage("[Assistant] Nothing to see here yet");
		
		return true;
	}
}
