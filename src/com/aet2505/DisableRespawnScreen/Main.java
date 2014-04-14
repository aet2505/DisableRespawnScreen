package com.aet2505.DisableRespawnScreen;

import com.aet2505.DisableRespawnScreen.versions.v1_6;
import com.aet2505.DisableRespawnScreen.versions.v1_7;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin
{
	public Main plugin;
	public Server bukkit;
	public Logger logger;
	private NMS nmsAccess;

    public static String getVersion()
    {
        return version;
    }

    private static String version;

	@Override
	public void onEnable()
	{
		plugin = this;
		bukkit = plugin.getServer();
		logger = plugin.getLogger();
		
		String packageName = bukkit.getClass().getPackage().getName();
		String[] packageSplit = packageName.split("\\.");
		version = packageSplit[((packageSplit.length)-1)];
        Class<?> listenerClass = v1_7.class;
		
		try
		{
            String[] versions = version.split("_");

            if ((versions[0].equals("v1")) && (Integer.parseInt(versions[1]) > 6))
            {
                listenerClass = v1_7.class;
            }
            else if ((versions[0].equals("v1") && (Integer.parseInt(versions[1]) <= 6)))
            {
                listenerClass = v1_6.class;
            }
//			Class<?> nmsClass = Class.forName("com.aet2505.DisableRespawnScreen.versions." + version);
//
//			if(NMS.class.isAssignableFrom(nmsClass))
//			{
//				this.nmsAccess = (NMS)nmsClass.getConstructor().newInstance();
//			}
			else
			{
                logger.severe("Unknown version. Please contact the author for " + version + " support.");
                plugin.setEnabled(false);
			}
		}
		catch (NumberFormatException e)
		{
			logger.severe("Unknown version. Please contact the author for " + version + " support.");
			plugin.setEnabled(false);
		}
			
		if (this.isEnabled() && NMS.class.isAssignableFrom(listenerClass))
		{
            try
            {
                nmsAccess = (NMS) listenerClass.getConstructor().newInstance();
                nmsAccess.registerDeathListener(this);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
		}
	}
}
