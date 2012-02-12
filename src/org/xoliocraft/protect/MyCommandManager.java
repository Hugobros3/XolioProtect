package org.xoliocraft.protect;


//import java.util.Collection;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.xoliocraft.protect.CuboidsList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

//import org.bukkit.inventory.ItemStack;
public class MyCommandManager implements CommandExecutor {

	public Protect plugin;
	public static Server server;
	public static Map<Player, Location> prevLoc = new HashMap<Player, Location>();
	
	public static List<LivingEntity> entities;
	
	public MyCommandManager(Protect instance) {
		plugin = instance;
		plugin.getCommand("xp").setExecutor(plugin);
		//plugin.getCommand("hp").setExecutor(plugin);
	}

	public MyCommandManager() {
		// Huh ?
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().contentEquals("xp") || cmd.getName().contentEquals("hp") ){
			
			Player player = (Player) sender;
			if(player.hasPermission("xoliocraft.admin") || player.isOp())
			{
			if(args.length == 1 && args[0].equalsIgnoreCase("save")){
				player.sendMessage(ChatColor.GREEN + "Saving cuboids file.");
				FileHandler.save();
				player.sendMessage(ChatColor.BLUE + "Done.");
				
			}
			if(args.length == 2 && args[0].equalsIgnoreCase("move")){
				player.sendMessage(ChatColor.YELLOW + "[Debug]Trying a move on "+args[1]);
				try {
					CuboidsList.getCuboid(args[1]).Move(null);
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "Error, this cuboid don't exist !");
				}
				player.sendMessage(ChatColor.BLUE + "Done.");
				
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("info")){
				player.sendMessage(ChatColor.RED + "Huh ... you may have spell it wrong... : /hp info <name> ");
				//init.test.getInfo(player);
		}
			if(args.length == 2 && args[0].equalsIgnoreCase("info")){
				String cuboidname = (String)args[1];
				player.sendMessage(ChatColor.BLUE + "Printing data for "+cuboidname);
				//init.test.getInfo(player);
				//Cuboid info = (Cuboid) init.QuietPlaces.get(cuboidname);
				try{
				CuboidsList.getCuboid(args[1]).getInfo(player);
				}
				catch(Exception exc){
				player.sendMessage(ChatColor.RED + "Error, this cuboid don't exist !");
				}
		}
			if(args.length == 1 && args[0].equalsIgnoreCase("create")){
				player.sendMessage(ChatColor.BLUE + "Seting an cuboid ...");
				player.sendMessage(ChatColor.BLUE + "Hit two blocks in the corners of the cuboid ...");
				player.sendMessage(ChatColor.BLUE + "When you've done , do /hp create NameOfCuboid");
				player.sendMessage(ChatColor.GOLD + "Or use /hp cancel");
				SelectionArea.setPlayerSelecting(player.getName(),true);
		}
			if(args.length == 2 && args[0].equalsIgnoreCase("create")){
				//Map<String,Integer> flags = new HashMap<String,Integer>();
				String cuboidname = (String)args[1];
				Cuboid placetocubo = new Cuboid(cuboidname);
				placetocubo.setProp("owner", player.getName());
				placetocubo.setProp("world", SelectionArea.getPlayerCords(player.getName())[1].getWorld().getName());
				placetocubo.setFlag("checktouch", 1);
				placetocubo.setFlag("nogrief", 1);
				placetocubo.setFlag("loggrief", 1);
				placetocubo.setSize(SelectionArea.getPlayerCords(player.getName()));
				//init.envoicubo(cuboidname, placetocubo);
				//init.QuietPlaces.put(cuboidname, placetocubo);
				//placetocubo.getInfo(player);
				CuboidsList.addCuboid(cuboidname, placetocubo);
				player.sendMessage(ChatColor.BLUE + "Cuboid "+cuboidname+" Created.");
				player.sendMessage(ChatColor.GREEN + "[INFO] By default , the cuboids are logged and protected.");
				//player.sendMessage(ChatColor.GREEN + "[INFO] Your cuboid is "+b1.getX()+" ; "+b1.getY()+";"+b1.getZ()+" - "+b2.getX()+" ; "+b2.getY()+";"+b2.getZ());
				SelectionArea.setPlayerSelecting(player.getName(),false);
				//init.save(init.QuietPlaces , "plugins/HugoPack/cuboids.dat");
				//init.save(init.QuietPlaces , "cuboids.dat");
		}
			if(args.length == 1 && args[0].equalsIgnoreCase("change")){
				player.sendMessage(ChatColor.GOLD + "Re-hit two blocks in corners");
				SelectionArea.setPlayerSelecting(player.getName(),true);
				player.sendMessage(ChatColor.GOLD + "When done type /change cuboname");
		}
		if(args.length == 2 && args[0].equalsIgnoreCase("change")){
				//String cuboidname = (String)args[1];
				try {
					CuboidsList.getCuboid(args[1]).setSize(SelectionArea.getPlayerCords(player.getName()));
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "Error, this cuboid don't exist !");
				}
				player.sendMessage(ChatColor.BLUE + "Cuboid redifined");
				SelectionArea.setPlayerSelecting(player.getName(),false);
	}
			if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
				player.sendMessage(ChatColor.RED + "Cuboid cancelled");
				SelectionArea.setPlayerSelecting(player.getName(),false);
		}
			if(args.length == 4 && args[0].equalsIgnoreCase("prop")){
				CuboidsList.setCuboidProp(args[1], args[2], args[3]);
				player.sendMessage("Property "+args[2]+" of cuboid "+args[1]+" set to "+args[3]);
				
		}
			if(args.length != 4 && args.length > 0 &&args[0].equalsIgnoreCase("prop")){
				player.sendMessage(ChatColor.RED+"Missing paramter.");
				
		}
			if(args.length == 1 && args[0].equalsIgnoreCase("list")){
				player.sendMessage(ChatColor.BLUE + "Listing...");
				player.sendMessage(ChatColor.BLUE + CuboidsList.getList());
		}
			if(args.length == 1 && args[0].equalsIgnoreCase("delete")){
				player.sendMessage(ChatColor.RED + "I miss a name.");
		}
			if(args.length == 2 && args[0].equalsIgnoreCase("delete")){
				player.sendMessage(ChatColor.BLUE + "Deleting "+args[1]+" .");
				//init.QuietPlaces.remove(args[1]);
				CuboidsList.delCuboid(args[1]);
		}
			if(args.length == 0){
				player.sendMessage(ChatColor.BLUE + "##XolioProtect##");
				//player.sendMessage(ChatColor.GREEN + "From last visit , X cuboids where entered , X guys kicked");
				player.sendMessage(ChatColor.RED + "Build : "+ChatColor.AQUA+plugin.getDescription().getVersion());
				player.sendMessage(ChatColor.WHITE + "****Commands****");
				player.sendMessage(ChatColor.RED + "/xp "+ChatColor.YELLOW+" => this menu.");
				player.sendMessage(ChatColor.RED + "/xp create <type> <name> "+ChatColor.YELLOW+" => Create a cuboid.");
				player.sendMessage(ChatColor.RED + "/xp delete <name> "+ChatColor.YELLOW+" => Delete a cuboid.");
				player.sendMessage(ChatColor.RED + "/xp info <name> "+ChatColor.YELLOW+" => See the log/info of a cuboid.");
				player.sendMessage(ChatColor.RED + "/xp change "+ChatColor.YELLOW+" => Change size of a cuboid.");
				player.sendMessage(ChatColor.RED + "/xp save => "+ChatColor.YELLOW+" Force to saves the cuboids.");
				player.sendMessage(ChatColor.RED + "/xp list => "+ChatColor.YELLOW+" List cuboids.");
				player.sendMessage(ChatColor.RED + "/xp prop <name> <prop> <value>"+ChatColor.YELLOW+" => Set a non-op owner.");
				player.sendMessage(ChatColor.RED + "/xp flag <name> <flag> <0/1> "+ChatColor.YELLOW+" => Enable or disable a cuboid's flag");
				//player.sendMessage(ChatColor.RED + "/hp misc => "+ChatColor.YELLOW+" Show various odds options , not related w/ cuboids");
			}
			if(args.length == 4 && args[0].equalsIgnoreCase("flag")){
				CuboidsList.setCuboidFlag(args[1], args[2], args[3]);
				player.sendMessage("Flag "+args[2]+" of cuboid "+args[1]+" set to "+args[3]);
			}
			}
			else
			{
			player.kickPlayer("Only operators / People with xoliocraft.admin can use this.");
			}
			return true;
			}
		return true;
	}

}