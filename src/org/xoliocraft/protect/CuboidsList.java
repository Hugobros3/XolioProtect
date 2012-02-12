package org.xoliocraft.protect;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.OfflinePlayer;

public class CuboidsList {

	private static HashMap<String, Cuboid> CuboidsMap = new HashMap<String, Cuboid>();;
	private static int cooldown = 100;
	public static Cuboid getCuboid(String name) throws Exception
	{
			return CuboidsMap.get(name);
		
	}
	
	public static void addCuboid(String name,Cuboid cubo)
	{
		CuboidsMap.put(name, cubo);
	}
	public static void delCuboid(String name)
	{
		CuboidsMap.remove(name);
	}
	public static String getList()
	{
		return CuboidsMap.keySet().toString();
	}
	public static void setCuboidFlag(String name,String flag,String to)
	{
		CuboidsMap.get(name).setFlag(flag, Integer.parseInt(to));
	}
	public static int getCuboidFlag(String name,String flag)
	{
		return CuboidsMap.get(name).getFlag(flag);
	}
	public static boolean checkPlayerMove(Player player)
	{
		
		
		Set<String> cubos = CuboidsMap.keySet();
		String cles[] = cubos.toArray(new String[0]);
		int n = cubos.size();
		int x = 0;
		boolean cancel = false;
		while(x < n)
		{
			Cuboid testable;
			testable = CuboidsMap.get(cles[x]);
			//player.sendMessage("test :"+cles[x]);
			if(testable.getFlag("checkenter") == 1){
				//player.sendMessage(cles[x]+" est vérifié");
			if(testable.isPlayerIn(player)){
				//player.sendMessage("t'est d'dans");
				if(!player.isOp() && !player.hasPermission("cuboid."+cles[x]) && !player.hasPermission("xoliocraft.override"))
				{
					String pname = player.getName();
					if(!pname.equals(testable.getOwner()))
					{
						if(testable.getFlag("killintruder") == 1){
						player.setHealth(0);
						player.sendMessage("You entered a autokill area !");
						}
						if(testable.getFlag("noenter") == 1){
							cancel = true;
							}
						if(testable.getFlag("kickintruder") == 1){
							player.kickPlayer("Ne pas entrer ? C'est pas assez clair ?");
							cancel = true;
							}
						if(testable.getFlag("banintruder") == 1){
							//Essentials test = (Essentials) server.getPluginManager().getPlugin("Essentials");
							//test.getUser(player.getName()).setBanTimeout(2000);
							cancel = true;
							OfflinePlayer mec = player.getPlayer();
							mec.setBanned(true);
							player.kickPlayer("On à dit ZONE INTERDITE !");
							
					}
						if(testable.getFlag("noticeintruder") == 1){
						if(cooldown > 100)
						{
						cooldown = 0;
						Protect.server.broadcastMessage(ChatColor.RED+player.getDisplayName()+" Is in "+cles[x]+"  !");
						Protect.log.info(player.getName()+"E"+cles[x]);
						}
						else
						{
							cooldown++;
						}
						//testable.setHim(player.getName());
					}
				}
				
			}
			else{

			}
			}
			}
			x++;
		}
		return cancel;
	}
	public static boolean checkBlock(Player player, Block block)
	{
		Set<String> cubos = CuboidsMap.keySet();
		String cles[] = cubos.toArray(new String[0]);
		int n = cubos.size();
		int x = 0;
		boolean cancel = false;
		while(x < n)
		{
			Cuboid testable;
			testable = CuboidsMap.get(cles[x]);
			//player.sendMessage("test :"+cles[x]);
			if(testable.getFlag("checktouch") == 1){
				//player.sendMessage(cles[x]+" est vérifié");
			if(testable.isBlockIn(block) ){
				
					
					if(!player.isOp() && !player.hasPermission("cuboid."+cles[x]) && !player.hasPermission("xolioprotect.override"))
					{
						String pname = player.getName();
						if(!pname.equals(testable.getProp("owner")))
						{
							if(testable.getFlag("killgrief") == 1)
							{
								player.setHealth(0);
								player.sendMessage("Grieffing here causes a kill. Sorry bro.");
							}
							if(testable.getFlag("kickgrief") == 1){
								player.kickPlayer("Pas de ca ici.");
							}
							if(testable.getFlag("bangrief") == 1){
								//Essentials test = (Essentials) server.getPluginManager().getPlugin("Essentials");
								//test.getUser(player.getName()).setBanTimeout(2000);
								
								OfflinePlayer mec = player.getPlayer();
								mec.setBanned(true);
	
								player.kickPlayer("Pas de ca ici.");
							}
							if(testable.getFlag("nogrief") == 1)
							{
								cancel = true;
							}
							if(testable.getFlag("loggrief") == 1)
							{
								Protect.log.info(player.getName()+"G"+cles[x]+"/"+block.getTypeId());
							}
							}
							if(testable.getFlag("noticegrief") == 1 && block.getType() != Material.STONE_PLATE && block.getType() != Material.WOOD_PLATE && block.getType() != Material.STONE_PLATE&& block.getType() != Material.WOOD_DOOR && block.getType() != Material.WOODEN_DOOR && block.getType() != Material.STONE_BUTTON)
							{
							Protect.server.broadcastMessage(ChatColor.RED+player.getDisplayName()+" Is grieffing in "+cles[x]+"  !");
							}
							//testable.setHim(player.getName());
						}
					}
				
			}
			x++;
			}
			
		
		return cancel;

	}

	public static HashMap<String, Cuboid> getRaw() {
		// TODO Auto-generated method stub
		return CuboidsMap;
	}

	public static void setCuboidProp(String string, String string2,
			String string3) {
		CuboidsMap.get(string).setProp(string2, string3);
		
	}
}
