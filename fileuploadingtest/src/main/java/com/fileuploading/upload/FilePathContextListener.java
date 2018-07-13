package com.fileuploading.upload;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


//@WebListener
public class FilePathContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {	
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
	String rootpath=System.getProperty("catalina.home");
	ServletContext context=event.getServletContext();
	String relativePath=context.getInitParameter("filedirectory");
	File file=new File(rootpath+File.separator+relativePath);
	if(!file.exists()) {
		file.mkdirs();
		System.out.println("directory created");
	}
	context.setAttribute("createfile",file);
	context.setAttribute("filelocation",rootpath+File.separator+relativePath);	
		
		

}
}