package org.xoliocraft.protect;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
public class anPlayerListener extends PlayerListener {
	
	public static Protect plugin;

	public static Server server = Bukkit.getServer();
	public anPlayerListener(Protect instance) {
		plugin = instance;
	}
	boolean logme = false;
	public void onPlayerMove(PlayerMoveEvent event){
		
		Player player = event.getPlayer();
		event.setCancelled(CuboidsList.checkPlayerMove(player));
	}
	public void onPlayerBucketFill(PlayerBucketEvent event)
	{
		Player player = event.getPlayer();
		Block bloc = event.getBlockClicked();
		event.setCancelled(CuboidsList.checkBlock(player, bloc));
	}
	public void onPlayerBucketEmpty(PlayerBucketEvent event)
	{
		Player player = event.getPlayer();
		Block bloc = event.getBlockClicked();
		event.setCancelled(CuboidsList.checkBlock(player, bloc));
	}
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.hasBlock()){
		Block bloc = event.getClickedBlock();
		event.setCancelled(CuboidsList.checkBlock(player, bloc));
		}
		
		if(player.isOp() || player.hasPermission("xoliocraft.pos")){
			/*if(Tools.getTool() == 2){
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
					Location loc = event.getClickedBlock().getLocation();
					player.getWorld().strikeLightning(loc);
				}
				*/
			}
			//Player player = event.getPlayer();
			String pname = player.getName();
			if(SelectionArea.isPlayerSelecting(pname) && player.getItemInHand().getType() == Material.WOOD_PICKAXE)
			{
				
				if(event.getAction() == Action.LEFT_CLICK_BLOCK)
				{
					Block bloc = event.getClickedBlock();
					SelectionArea.setPlayerCords(player.getName(), 0, bloc.getLocation());
					player.sendMessage(ChatColor.RED + "First set.");
				}
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					Block bloc = event.getClickedBlock();
					SelectionArea.setPlayerCords(player.getName(), 1, bloc.getLocation());
					player.sendMessage(ChatColor.RED + "Second set.");
				}
			}
			
				
		
	}
}
// -12 223 10