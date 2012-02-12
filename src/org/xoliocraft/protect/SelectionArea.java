package org.xoliocraft.protect;
import java.util.HashMap;

import org.bukkit.Location;
public class SelectionArea {
static public HashMap<String,Location[]> playersSelect = new HashMap<String, Location[]>();
static boolean isPlayerSelecting(String name)
{
	if(playersSelect.containsKey(name))
	{
		return true;
	}
	else
	{
		return false;
	}
}
static void setPlayerSelecting(String name, boolean b)
{
	if(b){
	if(!playersSelect.containsKey(name))
	{
	Location[] locarray = new Location[2];
	playersSelect.put(name, locarray);
	}
	}
	else
	{
		Protect.log.info("No more sel");
		if(playersSelect.containsKey(name))
		{
		playersSelect.remove(name);
		}
	}
}
static void setPlayerCords(String name, int cordsnum , Location cords)
{
	if(playersSelect.containsKey(name))
	{
		Location[] oldlocs = playersSelect.get(name);
		oldlocs[cordsnum] = cords;
		playersSelect.remove(name);
		playersSelect.put(name, oldlocs);
		Protect.log.info(oldlocs.toString());
	}
}
static Location[] getPlayerCords(String name)
{
	return playersSelect.get(name);
}
}
