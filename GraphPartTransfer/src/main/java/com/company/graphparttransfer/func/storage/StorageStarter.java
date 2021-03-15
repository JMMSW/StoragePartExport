
package com.company.graphparttransfer.func.storage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class StorageStarter implements ServletContextListener
{
	
	@Override
	public void contextInitialized(final ServletContextEvent sce)
	{
		DB.startStorages();
		
		if(DB.root1().getClients().isEmpty())
		{
			MockData.fillStorage1();
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce)
	{
	}
}
