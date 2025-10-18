package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    
    public static void main(String[] args) {
        
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "10000";
        }
        
        System.out.println("Starting server on port: " + port);
        
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.parseInt(port));
            
            // Set base directory
            String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
            tomcat.setBaseDir(baseDir);
            
            // Initialize connector
            tomcat.getConnector();
            
            // Setup webapp
            String webappPath = findWebappDirectory();
            System.out.println("Webapp path: " + webappPath);
            
            Context context = tomcat.addWebapp("", new File(webappPath).getAbsolutePath());
            context.setReloadable(false);
            
            System.out.println("Server configured successfully");
            
            // Start server
            tomcat.start();
            System.out.println("Server started on http://localhost:" + port);
            
            tomcat.getServer().await();
            
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static String findWebappDirectory() {
        String[] possiblePaths = {
            "src/main/webapp",
            "./src/main/webapp", 
            "/app/src/main/webapp",
            "webapp",
            "./webapp"
        };
        
        for (String path : possiblePaths) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                System.out.println("Found webapp at: " + dir.getAbsolutePath());
                return path;
            }
        }
        
        System.out.println("Webapp directory not found, creating default");
        File defaultDir = new File("webapp");
        defaultDir.mkdirs();
        
        return "webapp";
    }
}
