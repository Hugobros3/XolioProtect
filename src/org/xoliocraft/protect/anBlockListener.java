package org.xoliocraft.protect;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.Server;


public class anBlockListener extends BlockListener{

	//You HAVE to have this!
	public static Protect plugin;
	public static Server server = Bukkit.getServer();
	
	
	static boolean fol = true; // FirstOrLast block
	public anBlockListener(Protect instance) {
		plugin = instance;
	}
	//You HAVE to have this!
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		Block bloc = event.getBlock();
		event.setCancelled(CuboidsList.checkBlock(player, bloc));
	}
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		Block bloc = event.getBlock();
		event.setCancelled(CuboidsList.checkBlock(player, bloc));
	}
public void onSignChange(SignChangeEvent event) 
{
	Player player = event.getPlayer();
	//String[] txt = event.getLines();
	Location loc = event.getBlock().getLocation();
	Protect.log.info("[HugoPack]"+player.getName()+"/"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()+"/"+event.getLine(0)+event.getLine(1)+event.getLine(2)+event.getLine(3));
}


}