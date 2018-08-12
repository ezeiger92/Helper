package com.chromaclypse.helper;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.Permissible;

import com.chromaclypse.api.messages.Text;
import com.chromaclypse.helper.match.LevenMatch;
import com.chromaclypse.helper.match.LevenMatcher;
import com.chromaclypse.helper.match.LevenResult;

public class CloseCommand implements Listener {
	private static SimpleCommandMap commandMap;
	static {
		try {
			String className = Bukkit.getServer().getClass().getName();
			commandMap = (SimpleCommandMap) Class.forName(className).getMethod("getCommandMap").invoke(Bukkit.getServer());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private HelperConfig config;
	
	public CloseCommand(HelperConfig config) {
		this.config = config;
	}
	
	private boolean hasPerm(Permissible source, Command command) {
		String perm = command.getPermission();
		return !config.suggest.permFilter || perm == null || source.hasPermission(perm);
	}
	
	public void unregister() {
		PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
	}
	
	public LevenResult matchCommand(String cmdWithSlash, Permissible source) {
		return new LevenMatcher()
				.matching(cmdWithSlash.substring(1).toLowerCase())
				.searching(commandMap.getCommands(), c -> hasPerm(source, c) ? c.getName() : null)
				.searching(config.suggest.unlistedCommands)
				.filtering(m -> m.getDistance() > config.suggest.maxRange)
				.get();
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void preCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String[] args = e.getMessage().split(" ", 2);
		
		String command = args[0];
		String rest = args.length < 2 ? "" : " " + args[1];
		
		if(commandMap.getCommand(command.substring(1)) != null)
			return;
		
		LevenResult results = matchCommand(command, player);
		List<LevenMatch> matches = results.getMatches();
		
		if(matches.isEmpty())
			return;
		
		LevenMatch first = matches.get(0);
		matches.removeIf(m -> m.getDistance() > first.getDistance() + 1);
		
		if(results.isExact())
			e.setMessage("/" + first.getString() + rest);
		
		else {
			int len = Math.min(matches.size(), config.suggest.maxResults);
			StringBuilder line = new StringBuilder("&cUnknown command \"" + command + "\".");
			
			if(len > 0) {
				line.append("&7Did you mean: &f").append(matches.get(0).getString());
				for(int i = 0; i < len; ++i)
					line.append("&7, &f").append(matches.get(i).getString());
			}
			
			player.sendMessage(Text.colorize(line.toString()));
			e.setCancelled(true);
		}
	}
}
