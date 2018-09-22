package io.orthrus.server.rest;

import io.orthrus.server.rest.container.ContainerManager;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = ResourceServerConfiguration.class)
public class ResourceServerConfiguration {

   private final ResourceServer server;
   
   public ResourceServerConfiguration(
         @Value("${server.scan-packages:com.orthrus.server.rest}") String packages,
         @Value("${server.directory}") File directory,
         @Value("${server.port}") int port,
         @Value("${server.swagger.enabled:true}") boolean swagger)
   {
      this.server = new ResourceServer(packages, directory, port, swagger);
   }
   
   @Bean
   public ApplicationListener<ContextRefreshedEvent> applicationListener() {
      return (event) -> {
        try {
           ApplicationContext context = event.getApplicationContext();
           ContainerManager endPoint = server.start(context);
           int port = endPoint.getPort();
           
           log.info("Listening on {}", port);
        } catch(Exception e) {
           log.error("Could not launch server", e);
        }
      };
   }
}
