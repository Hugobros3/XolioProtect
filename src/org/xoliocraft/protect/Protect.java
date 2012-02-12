package org.xoliocraft.protect;
// XolioProtect, custom plugin to protect zones, check out xoliocraft.org
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.xoliocraft.protect.FileHandler;
import org.xoliocraft.protect.anEntityListener;
import org.xoliocraft.protect.MyCommandManager;
import org.xoliocraft.protect.anBlockListener;
import org.xoliocraft.protect.anPlayerListener;
public class Protect extends JavaPlugin{
    //ClassListeners
    private final anPlayerListener playerListener = new anPlayerListener(this);
	private final anBlockListener blockListener = new anBlockListener(this);
	private final anEntityListener entityListener = new anEntityListener(this);
    //logger & server
	static Logger log = Logger.getLogger("Minecraft");//Define your logger
	public static Server server = Bukkit.getServer();
	// ******** init *************
	public void onDisable() {
		FileHandler.save();
		log.info("[Hugopack]Hugopack has terminated its operations.");
		// save
	}
	// Enable
	public void onEnable() {
        log.info("[XolioProtect]Hugopack by Hugobros3 , visit xoliocraft.org.");
        log.info("[XolioProtect][Version:"+this.getDescription().getVersion()+"]Initialising...");
        //commands = new MyCommandManager(this);
        CommandExecutor man =  new MyCommandManager(this);
    	getCommand("xp").setExecutor(man);
    	//getCommand("nukeitems").setExecutor(man);
        PluginManager pm = this.getServer().getPluginManager();
        log.info("[XolioProtect]Command manager initialised.");
		// Register events
        
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Low, this);
		pm.registerEvent(Event.Type.PLAYER_BUCKET_FILL, playerListener, Event.Priority.Low, this);
		pm.registerEvent(Event.Type.PLAYER_BUCKET_EMPTY, playerListener, Event.Priority.Low, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Low, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Low, this);
		pm.registerEvent(Event.Type.SIGN_CHANGE , blockListener , Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE , blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK , blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener , Event.Priority.Normal , this);
		FileHandler.load();
		log.info("[XolioProtect]Ready , To serve and protect.");
	}
}
