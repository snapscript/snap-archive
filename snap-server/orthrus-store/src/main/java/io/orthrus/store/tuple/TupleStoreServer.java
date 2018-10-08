package io.orthrus.store.tuple;

import io.orthrus.common.AnnotationPresentScanner;
import io.orthrus.common.ClassPathScanner;
import io.orthrus.store.Entity;

import java.net.InetAddress;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;

import com.zuooh.tuple.frame.SessionRegistry;
import com.zuooh.tuple.grid.Catalog;
import com.zuooh.tuple.subscribe.SubscriptionListener;

public class TupleStoreServer {
   
   private final TupleStoreSubscriber subscriber;
   private final ClassPathScanner scanner;
   private final CatalogBuilder builder;
   
   public TupleStoreServer(List<SubscriptionListener> listeners, String packages, String name, String remote, int port) {
      this.scanner = new AnnotationPresentScanner(Entity.class, "", packages);
      this.builder = new CatalogBuilder(listeners, port);
      this.subscriber = new TupleStoreSubscriber(name, remote, port);
   }
   
   @SneakyThrows
   public TupleStoreBuilder start(SessionRegistry registry) {
      Set<Class<?>> matches = scanner.scan();
      Catalog catalog = builder.create(registry, matches);
      InetAddress address = InetAddress.getLocalHost();
      String host = address.getCanonicalHostName();
      
      return subscriber.subscribe(registry, catalog, host);
   }
}
