package net.shine.exemple.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bosniemc.shine.api.manager.PlayerManager;

public class PlayerInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {
		if(s.hasPermission("player.getinfo")){
			if(args.length == 0){
				s.sendMessage("§cErreur: /playerinfo [Joueur]");
				return false;
			}
			if(Bukkit.getPlayer(args[0]) != null){
				Player p = Bukkit.getPlayer(args[0]);
				PlayerManager pm = new PlayerManager(p);
				s.sendMessage("§e§m----------------------");
				s.sendMessage("§c");
				s.sendMessage("§6Pseudo: §e"+p.getName());
				s.sendMessage("§6Version: §e"+pm.getVersion().getVersionName());
				s.sendMessage("§6Ip: §e"+p.getAddress().getHostName());
				if(pm.getVersion().getVersionid() < 6){
					s.sendMessage("§6Version avec changement: §aOui");
				}
				else{
					s.sendMessage("§6Version avec changement: §cNon");
				}
				s.sendMessage("§c");
				s.sendMessage("§e§m----------------------");
				return true;
			}
			else{
				s.sendMessage("§cErreur: Le joueur n'est pas connecté !");
				return false;
			}
		}
		else{
			s.sendMessage("§cErreur: Vous n'avez pas la permission d'éxécutez cette commande !");
			return false;
		}
	}

}
