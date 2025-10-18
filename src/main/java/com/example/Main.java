package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    
    public static void main(String[] args) throws LifecycleException {
        
        // Get port from environment or default to 8080
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080";
        }
        
        System.out.println("Starting server on port: " + port);
        
        // Create Tomcat instance
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getConnector();
        
        // Setup webapp directory
        String webappDirLocation = "src/main/webapp/";
        Context ctx = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        
        // Declare an alternative location for compiled classes
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
            new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/")
        );
        ctx.setResources(resources);
        
        System.out.println("Webapp directory: " + new File(webappDirLocation).getAbsolutePath());
        System.out.println("Server is ready...");
        
        // Start server
        tomcat.start();
        tomcat.getServer().await();
    }
}