package com.example.tinypet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class TinypetBootstrapper implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		ObjectifyService.init();
		ObjectifyService.register(Petition.class);
		ObjectifyService.register(Aggregate.class);
		ObjectifyService.register(Signature.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}  