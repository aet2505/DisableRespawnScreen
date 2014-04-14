package com.aet2505.DisableRespawnScreen.versions;

import com.aet2505.DisableRespawnScreen.Main;
import com.aet2505.DisableRespawnScreen.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by adam on 14/04/14.
 */
public class v1_7 implements NMS, Listener
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
                    try
                    {
                        Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
                        Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");

                        for(Object ob : enumClass.getEnumConstants()){
                            if(ob.toString().equals("PERFORM_RESPAWN")){
                                packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
                            }
                        }

                        Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
                        con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
