package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
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
        
        // Get webapp directory
        String webappDirLocation = getWebappDirLocation();
        System.out.println("Webapp directory: " + webappDirLocation);
        
        // Add webapp context
        Context ctx = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        
        System.out.println("Configuring app with basedir: " + new File(".").getAbsolutePath());
        
        // Start server
        tomcat.start();
        System.out.println("Server started successfully on port " + port);
        tomcat.getServer().await();
    }
    
    private static String getWebappDirLocation() {
        // Try multiple possible locations
        String[] possiblePaths = {
            "src/main/webapp/",
            "./src/main/webapp/",
            "../src/main/webapp/",
            "webapp/",
            "./webapp/"
        };
        
        for (String path : possiblePaths) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                System.out.println("Found webapp directory at: " + path);
                return path;
            }
        }
        
        // Default fallback
        System.out.println("Using default webapp path: src/main/webapp/");
        return "src/main/webapp/";
    }
}
