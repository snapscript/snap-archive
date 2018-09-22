package io.orthrus.server.rest.container;

import io.orthrus.server.rest.content.ContentHandlerMatcher;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Application;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.simple.SimpleContainer;
import org.glassfish.jersey.simple.SimpleTraceAnalyzer;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.http.socket.service.DirectRouter;
import org.simpleframework.http.socket.service.Router;
import org.simpleframework.http.socket.service.RouterContainer;
import org.simpleframework.http.socket.service.Service;
import org.simpleframework.transport.SocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class ContainerManagerBuilder {

   private final ContentHandlerMatcher matcher;
   private final Router router;
   
   public ContainerManagerBuilder(ContentHandlerMatcher matcher){
      this(matcher, null);
   }
   
   public ContainerManagerBuilder(ContentHandlerMatcher matcher, Service service) {
      this.router = new DirectRouter(service);
      this.matcher = matcher;
   }
   
   @SneakyThrows
   public ContainerManager create(ResourceConfig config, SSLContext context, int port) {
      SimpleContainer container = create(config);
      Container inner = create(container);
   
      try {
         SimpleTraceAnalyzer analyzer = new SimpleTraceAnalyzer();
         SocketProcessor server = new ContainerSocketProcessor(inner);
         Connection connection = new SocketConnection(server, analyzer);
         InetSocketAddress listen = new InetSocketAddress(port);
         InetSocketAddress bound = (InetSocketAddress)connection.connect(listen, context);
         int bindPort = bound.getPort();
         
         container.getApplicationHandler().onStartup(container);
         
         return new ServerContainerManager(analyzer, container, connection, bindPort);
      } catch(IOException e) {
         throw new ProcessingException("Problem starting server", e);
      }
   }
   
   private Container create(SimpleContainer container) {
      try {
         ResourceContainer delegate = new ResourceContainer(matcher, container);
         
         if(router != null) {
            return new RouterContainer(delegate, router, 5);
         }
         return delegate;
      } catch(Exception e) {
         throw new IllegalStateException("Could not create router container", e);
      }
   }

   private SimpleContainer create(ResourceConfig config) {
      try {
         Constructor<SimpleContainer> constructor = SimpleContainer.class.getDeclaredConstructor(Application.class);
         
         constructor.setAccessible(true);
         return constructor.newInstance(config); 
      }catch(Exception e) {
         throw new IllegalStateException("Could not create container", e);
      }
   }
   
   @AllArgsConstructor
   private static class ServerContainerManager implements ContainerManager {
      
      private final SimpleTraceAnalyzer analyzer;
      private final SimpleContainer container;
      private final Connection connection;
      private final int port;
      
      @Override
      public void close() throws IOException {
         container.getApplicationHandler().onShutdown(container);
         analyzer.stop();
         connection.close();
      }
      
      @Override
      public int getPort() {
         return port;
      }
      
      @Override
      public boolean isDebug() {
         return analyzer.isActive();
      }
      
      @Override
      public void setDebug(boolean enable) {
         if(enable) {
            analyzer.start();
         } else {
            analyzer.stop();
         }
      }
   }
}
