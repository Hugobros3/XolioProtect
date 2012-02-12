package org.xoliocraft.protect;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
//import org.bukkit.block.*;
public class Cuboid {
	@SuppressWarnings("unused")
	private String name;
	private HashMap<String,Integer> flags = new HashMap<String,Integer>();
	private HashMap<String,String> props = new HashMap<String,String>() ;
	public boolean isPlayerIn(Player player)
	{
		Location whereishe = player.getLocation();
		//player.sendMessage("testing you in "+whereishe.getWorld().getName());
		
		
		if(whereishe.getWorld().getName().equals(props.get("world")))
		{
			//init.log.info(sx+"/"+ex+" "+sy+"/"+ey+" "+sz+"/"+ez);
			if(whereishe.getX() >= Math.min((flags.get("sx")),(flags.get("ex"))) && whereishe.getX() <= Math.max((flags.get("sx")),(flags.get("ex"))))
			{
				//init.log.info("X ok");
				if(whereishe.getY() >= Math.min((flags.get("sy")),(flags.get("ey"))) && whereishe.getY() <= Math.max((flags.get("sy")),(flags.get("ey"))))
				{
					//init.log.info("Y ok");
					if(whereishe.getZ() >= Math.min((flags.get("sz")),(flags.get("ez"))) && whereishe.getZ() <= Math.max((flags.get("sz")),(flags.get("ez"))))
					{
						//init.log.info("nice your done");
								return true;

					}
				}
			}
		}
		//return false;
		return false;
		
	}
	public boolean isBlockIn(Block block)
	{
		Location whereishe = block.getLocation();
		//player.sendMessage("testing you in "+whereishe.getWorld().getName());
		//int rsx, rsy,rsz ;
		//int xw,yw,zw;
		//rsx = Math.min(sx,ex);
		//xw = Math.max(sx,ex)-Math.min(sx,ex);
		//System.out.print("X start in "rsx+" znd is "+xw+" long");
		if(whereishe.getWorld().getName().equals(props.get("world")))
		{
			//init.log.info(sx+"/"+ex+" "+sy+"/"+ey+" "+sz+"/"+ez);
			if(whereishe.getX() >= Math.min((flags.get("sx")),(flags.get("ex"))) && whereishe.getX() <= Math.max((flags.get("sx")),(flags.get("ex"))))
			{
				//init.log.info("X ok");
				if(whereishe.getY() >= Math.min((flags.get("sy")),(flags.get("ey"))) && whereishe.getY() <= Math.max((flags.get("sy")),(flags.get("ey"))))
				{
					//init.log.info("Y ok");
					if(whereishe.getZ() >= Math.min((flags.get("sz")),(flags.get("ez"))) && whereishe.getZ() <= Math.max((flags.get("sz")),(flags.get("ez"))))
					{
						//init.log.info("nice your done");
								return true;

					}
				}
			}
		}
		return false;
		
	}
	public Cuboid(String pname)
	{
		name=pname;
	}

	public void getInfo(Player sender)
	{
		int sizex = Math.abs((flags.get("sx"))-(flags.get("ex")))+1;
		int sizey = Math.abs((flags.get("sy"))-(flags.get("ey")))+1;
		int sizez = Math.abs((flags.get("sz"))-(flags.get("ez")))+1;
		int size = sizex*sizey*sizez;
		sender.sendMessage(ChatColor.GREEN+"----[INFO ABOUT THIS CUBOID]----");
		sender.sendMessage(ChatColor.GREEN+"Size : "+size+" ("+sizex+" ; "+sizey+" ; "+sizez+")");
		sender.sendMessage(ChatColor.GREEN+"Position : "+(flags.get("sx"))+" ; "+(flags.get("sy"))+" ; "+(flags.get("sz")));
		sender.sendMessage(ChatColor.GREEN+"Owner : "+props.get("owner"));
		sender.sendMessage(ChatColor.GREEN+"World : "+props.get("world"));
	}
	public String getOwner()
	{
		return props.get("owner");
	}
	public void setOwner(String nowner)
	{
		setProp("owner",nowner);
	}
	public int getFlag(String flag)
	{
		if(flags.containsKey(flag))
			return flags.get(flag);
		return 0;
	}
	public void setFlag(String flag, int state)
	{
		if(flags.containsKey(flag))
			flags.remove(flag);
		flags.put(flag, state);
	}
	public Map<String,Integer> getAllFlags() {
		return flags;
	}
	public Map<String,String> getAllProps() {
		return props;
	}
	public int[] getPos()
	{
		//int pos[] = {sx,sy,sz,ex,ey,ez};
		int pos[] = {(flags.get("sx")),(flags.get("sy")),(flags.get("sz")),(flags.get("ex")),(flags.get("ey")),(flags.get("ez"))};
		return pos;
	}
	public void setProp(String flag, String state)
	{
		if(props.containsKey(flag))
			props.remove(flag);
		props.put(flag, state);
	}
	// Version 50
	public void setSize(Location[] newcords) {
		
		setFlag("sx",newcords[0].getBlockX());
		setFlag("sy",newcords[0].getBlockY());
		setFlag("sz",newcords[0].getBlockZ());
		setFlag("ex",newcords[1].getBlockX());
		setFlag("ey",newcords[1].getBlockY());
		setFlag("ez",newcords[1].getBlockZ());
		
	}
	public String getProp(String string) {
		return props.get(string);
	}
	public void Move(Location newloc)
	{
		Block array[] = null;
		int a = 0,b = 0,c = 0 ;
		int cux,cuy,cuz;
		cux = flags.get("sx");
		cuy = flags.get("sy");
		cuz = flags.get("sz");
		Server server = Bukkit.getServer();
		//Location loc = new org.bukkit.Location(init.server.getWorld(getProp("world")), cux, cuy, cuz);
		while(a < 1+Math.abs((flags.get("sx"))-(flags.get("ex"))))
		{
			b = 0;
			while(b < 1+Math.abs((flags.get("sy"))-(flags.get("ey"))))
			{
				c = 0;
				while(c < 1+Math.abs((flags.get("sz"))-(flags.get("ez"))))
				{
					System.out.print("[HugoPack][Debug]Saving Block in "+cux+" , "+cuy+" , "+cuz);
					//loc = new org.bukkit.Location(init.server.getWorld(getProp("world")), cux, cuy, cuz);
					try{
						server.getWorld(getProp("world"));
						//init.server.getWorld(getProp("world"));
						if(server.getWorld(getProp("world")).getBlockAt(cux, cuy, cuz) != null)
							System.out.print(" ,(Material ="+server.getWorld(getProp("world")).getBlockTypeIdAt(cux, cuy, cuz)+")");
						server.getWorld(getProp("world")).getBlockAt(cux, cuy, cuz).setTypeId(0);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
								
					}
					System.out.println("Kay.");
					//array[cux*((flags.get("sy"))-(flags.get("ey")))*((flags.get("sy"))-(flags.get("ey")))] = server.getWorld(getProp("world")).getBlockAt(cux, cuy , cuz);
					
					if((flags.get("sz"))<flags.get("ez"))
							cuz++;
					else
						cuz--;
					c++;	
				}
				if((flags.get("sy"))<flags.get("ey"))
					cuy++;
			else
				cuy--;
				b++;
			}
			if((flags.get("sx"))<flags.get("ex"))
				cux++;
			else
				cux--;
			a++;
		}
	}
}
