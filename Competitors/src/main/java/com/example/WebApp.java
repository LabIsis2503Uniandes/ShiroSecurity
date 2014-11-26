package com.example;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class WebApp extends ResourceConfig {
	public WebApp(){
        packages("com.example.services");
    }
}