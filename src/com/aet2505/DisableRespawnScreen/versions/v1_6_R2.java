package com.aet2505.DisableRespawnScreen.versions;

import net.minecraft.server.v1_6_R2.Packet205ClientCommand;

import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.aet2505.DisableRespawnScreen.Main;
import com.aet2505.DisableRespawnScreen.NMS;

public class v1_6_R2 implements NMS, Listener
{
	private Main plugin;
	
	@Override
	public void registerDeathListener(Main plugin)
	{
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.logger.info("Death Listener Registered");
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
	    final Player player = e.getEntity();
	    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
	    { 
	    	public void run() 
		    {
		    	if(player.isDead())
		    	{
		    		Packet205ClientCommand packet = new Packet205ClientCommand();
                    packet.a = 1;
                    ((CraftPlayer) player).getHandle().playerConnection.a(packet);
		    	}
		    }
	    });
	}
}
