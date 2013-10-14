package com.aet2505.DisableRespawnScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public Main plugin;
	public Server bukkit;
	public Logger logger;
	private NMS nmsAccess;

	@Override
	public void onEnable()
	{
		plugin = this;
		bukkit = plugin.getServer();
		logger = plugin.getLogger();
		
		String packageName = bukkit.getClass().getPackage().getName();
		String[] packageSplit = packageName.split("\\.");
		String version = packageSplit[((packageSplit.length)-1)];
		
		try
		{
			Class<?> nmsClass = Class.forName("com.aet2505.DisableRespawnScreen.versions." + version);
			
			if(NMS.class.isAssignableFrom(nmsClass))
			{
				this.nmsAccess = (NMS)nmsClass.getConstructor().newInstance();
			}
			else
			{
				logger.severe("ERROR: Please report this and with the MC version and the error");
				plugin.setEnabled(false);
			}
		}
		catch (ClassNotFoundException e)
		{
			logger.severe("Unknown version. Please contact the author for " + version + " support.");
			plugin.setEnabled(false);
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
			logger.severe("ERROR: Please report this and with the MC version and the error");
			plugin.setEnabled(false);
		}
			
		if (this.isEnabled())
		{
			nmsAccess.registerDeathListener(this);
		}
	}
	
	@Override
	public void onDisable()
	{
	
	}
}
