package net.shine.exemple.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import bosniemc.shine.api.builder.ItemBuilder;
import bosniemc.shine.api.builder.LeatherArmorBuilder;
import bosniemc.shine.api.builder.SkullBuilder;
import bosniemc.shine.api.builder.TitleBuilder;
import bosniemc.shine.api.manager.PlayerManager;
import bosniemc.shine.api.utils.ArmorType;
import bosniemc.shine.api.utils.ScoreBoardSign;

public class Events implements Listener {
	
	public HashMap<String, ScoreBoardSign> sbs = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage("§e"+e.getPlayer().getName()+" viens de rejoindre le serveur !");
		for(ScoreBoardSign sb : sbs.values()){
			sb.setLine(13, "§dConnecter: "+Bukkit.getServer().getOnlinePlayers().size());
		}
		ScoreBoardSign sb = new ScoreBoardSign(e.getPlayer(), "§eExemple");
		sb.create();
		sb.setLine(0, "§c");
		sb.setLine(1, "§6Pseudo: §e"+e.getPlayer().getName());
		sb.setLine(2, "§e");
		if(e.getPlayer().isOp()){
			sb.setLine(3, "§dRang: §cAdmin");
		}
		if(!e.getPlayer().isOp()){
			if(e.getPlayer().hasPermission("sb.staff")){
				sb.setLine(4, "§dRang: §aStaff");
			}
			else{
				sb.setLine(5, "§dRang: §7Joueur");
			}
		}
		sb.setLine(6, "§8");
		sb.setLine(7, "§eCoins: §f0");
		sb.setLine(8, "§6");
		sb.setLine(9, "§dConnecter: "+Bukkit.getServer().getOnlinePlayers().size());
		sb.setLine(10, "§a");
		sbs.put(e.getPlayer().getName(), sb);
		
		PlayerInventory inv = e.getPlayer().getInventory();
		inv.clear();
		inv.setItem(0, new SkullBuilder(e.getPlayer().getName()).setName("§cProfil de "+e.getPlayer().getName()).build());
		inv.setItem(4, new ItemBuilder(Material.COMPASS).setName("§6Navigation").build());
		inv.setItem(8, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§cParamètre").build());
		inv.setChestplate(new LeatherArmorBuilder(ArmorType.CHESTPLATE).setColor(Color.YELLOW).setName("§eGilet Jaune").build());
		
		TitleBuilder ti = new TitleBuilder("§f< §eExemple §f>").setFadeIn(20).setFadeOut(20).setShowTime(20).setSubTitle("§eServeur Mini-Jeux");
		ti.sendToPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage("§e"+e.getPlayer().getName()+" viens de quitter le serveur !");
		sbs.remove(e.getPlayer().getName());
		for(ScoreBoardSign sb : sbs.values()){
			sb.setLine(13, "§dConnecter: "+Bukkit.getServer().getOnlinePlayers().size());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getItem() == null) return;
		if(e.getItem().getItemMeta().getDisplayName().equals("§6Navigation")){
			Inventory inv = Bukkit.createInventory(null, 9, "§6Navigation");
			inv.setItem(3, new ItemBuilder(Material.DIAMOND_AXE).setName("§cKitPvP").addLore("§cCommbatez vous dans une arène").addLore("§cet faîtes le maximum de kill possible !").build());
			inv.setItem(5, new ItemBuilder(Material.BED).setName("§cRush").addLore("§cRamasser des ressources").addLore("§cpour avoir un maximum de stuff").addLore("§cet casser le lit de votre adversaire").addLore("§cpuis tuer le pour gagner !").build());
			((Player) e.getPlayer()).openInventory(inv);
		}
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e){
		if(e.getInventory() == null) return;
		if(e.getCurrentItem() == null) return;
		if(e.getInventory().getName().equals("§6Navigation")){
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.DIAMOND_AXE){
				PlayerManager pm = new PlayerManager(((Player) e.getWhoClicked()));
				pm.sendToServer("kitpvp");
				e.getWhoClicked().sendMessage("§aDirection Serveur KitPvP");
				return;
			}
			if(e.getCurrentItem().getType() == Material.BED){
				PlayerManager pm = new PlayerManager(((Player) e.getWhoClicked()));
				pm.sendToServer("rush");
				e.getWhoClicked().sendMessage("§aDirection Serveur Rush");
				return;
			}
		}
		
	}
	
	

}
