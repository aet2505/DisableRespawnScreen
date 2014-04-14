package com.aet2505.DisableRespawnScreen.versions;

import com.aet2505.DisableRespawnScreen.Main;
import com.aet2505.DisableRespawnScreen.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.Field;

/**
 * Created by adam on 14/04/14.
 */
public class v1_6 implements NMS, Listener
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
                    String version = Main.getVersion();
                    try {
                        Class<?> packet = Class.forName("net.minecraft.server." + version.replace(".", "_") + ".Packet205ClientCommand");
                        Object name = packet.getConstructor(new Class[0]).newInstance(new Object[0]);
                        Field a = packet.getDeclaredField("a");
                        a.setAccessible(true);
                        a.set(name, 1);
                        Object nmsPlayer = Class.forName("org.bukkit.craftbukkit."+version.replace(".", "_")+".entity.CraftPlayer").getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
                        Field con = Class.forName("net.minecraft.server."+version.replace(".", "_") +".EntityPlayer").getDeclaredField("playerConnection");
                        con.setAccessible(true);
                        Object handle = con.get(nmsPlayer);
                        packet.getDeclaredMethod("handle", Class.forName("net.minecraft.server."+version.replace(".", "_") +".Connection")).invoke(name, handle);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
