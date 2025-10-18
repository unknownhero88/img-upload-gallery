package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    
    public static void main(String[] args) throws LifecycleException, IOException {
        
        // Get port
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "10000";
        }
        
        System.out.println("==> Starting Tomcat on port: " + port);
        
        // Create Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.setBaseDir(createTempDir());
        tomcat.getConnector();
        
        // Setup webapp
        String webappDir = getWebappPath();
        System.out.println("==> Webapp directory: " + webappDir);
        
        Context ctx = tomcat.addWebapp("", new File(webappDir).getAbsolutePath());
        
        // Don't require WEB-INF/classes directory
        WebResourceRoot resources = new StandardRoot(ctx);
        ctx.setResources(resources);
        
        System.out.println("==> Server configured successfully");
        
        // Start
        tomcat.start();
        System.out.println("==> Server started! Visit http://localhost:" + port);
        tomcat.getServer().await();
    }
    
    private static String getWebappPath() {
        // Check if we're in JAR or development
        String[] paths = {
            "src/main/webapp",
            "./src/main/webapp",
            "webapp",
            "./webapp",
            "/app/src/main/webapp"
        };
        
        for (String path : paths) {
            File f = new File(path);
            if (f.exists() && f.isDirectory()) {
                System.out.println("==> Found webapp at: " + f.getAbsolutePath());
                return path;
            }
        }
        
        // Create webapp directory if doesn't exist
        File webapp = new File("webapp");
        if (!webapp.exists()) {
            webapp.mkdirs();
        }
        
        return "webapp";
    }
    
    private static String createTempDir() {
        try {
            Path tempDir = Files.createTempDirectory("tomcat");
            tempDir.toFile().deleteOnExit();
            return tempDir.toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temp directory", e);
        }
    }
}
