package net.shine.exemple;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import bosniemc.shine.api.builder.ActionBarBuilder;
import net.shine.exemple.commands.PlayerInfo;
import net.shine.exemple.events.Events;

public class Main extends JavaPlugin{
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		getCommand("playerinfo").setExecutor(new PlayerInfo());
		getServer().getPluginManager().registerEvents(new Events(), this);
		Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {
			
			@Override
			public void run() {
				ActionBarBuilder act = new ActionBarBuilder("§eBienvenue sur Exemple !").setFadeIn(20).setShowTime(20).setFadeOut(20);
				for(Player p : Bukkit.getOnlinePlayers()){
					act.sendToPlayer(p);
				}
			}
		}, 1200, 1200);
	}
}
