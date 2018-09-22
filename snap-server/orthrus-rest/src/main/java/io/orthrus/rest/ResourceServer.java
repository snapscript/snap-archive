package io.orthrus.rest;

import io.orthrus.rest.container.ContainerManager;
import io.orthrus.rest.container.ContainerManagerBuilder;
import io.orthrus.rest.container.DependencyManager;
import io.orthrus.rest.content.ContentHandlerMatcher;
import io.orthrus.rest.content.FileContentHandlerMatcher;

import java.io.File;

import lombok.SneakyThrows;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class ResourceServer {
   
   private final ContainerManagerBuilder builder;
   private final ContentHandlerMatcher matcher;
   private final DependencyManager manager;
   private final int port;
   
   public ResourceServer(String packages, File directory, int port, boolean swagger) {
      this.matcher = new FileContentHandlerMatcher(directory);
      this.manager = new DependencyManager(packages, swagger);
      this.builder = new ContainerManagerBuilder(matcher);
      this.port = port;
   }

   @SneakyThrows
   public ContainerManager start(ApplicationContext context) {
      ResourceConfig config = manager.start((ConfigurableApplicationContext)context);
      return builder.create(config, null, port);
   }
}
