package org.xoliocraft.protect;


import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.xoliocraft.protect.CuboidsList;

public class anEntityListener extends EntityListener {
	
	public static Protect plugin;

	public static Server server = Bukkit.getServer();
	public anEntityListener(Protect instance) {
		plugin = instance;
	}
	public void onEntityDamage(EntityDamageEvent event)
		{
			Entity entity = event.getEntity();
			if(entity instanceof Player){
				Player player = (Player) entity;
				if(event.getCause().equals(DamageCause.ENTITY_ATTACK))
				{
					if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)
		        	{
						Entity attacker = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
		    			if(attacker instanceof Player){
		    				Player killer = (Player) attacker;
							//if(player.getLastDamageCause().getDamager())
							Set<String> cubos = CuboidsList.getRaw().keySet();
							String cles[] = cubos.toArray(new String[0]);
							int n = cubos.size();
							int x = 0;
							while(x < n)
							{
								Cuboid testable = null;
								try {
									testable = CuboidsList.getCuboid(cles[x]);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//player.sendMessage("test :"+cles[x]);
								if(testable.getFlag("checkenter") == 1){
									//player.sendMessage(cles[x]+" est vérifié");
									if(testable.isPlayerIn(player)){
										if(testable.getFlag("nopvp") == 1)
										{
										event.setCancelled(true);
										killer.sendMessage(ChatColor.RED+"You're in a non-pvp area.");
										}
									}
								}
								x++;
							}
			        	}
		        	}
				}
			}
		}
	}