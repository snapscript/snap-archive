package io.orthrus.store.tuple;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import jetbrains.exodus.entitystore.PersistentEntityStore;

import com.zuooh.tuple.TupleListener;
import com.zuooh.tuple.TuplePublisher;
import com.zuooh.tuple.grid.Catalog;
import com.zuooh.tuple.grid.GridPublisher;
import com.zuooh.tuple.query.Origin;
import com.zuooh.tuple.query.Query;
import com.zuooh.tuple.subscribe.Subscriber;

class PersistentStoreBuilder {
   
   private final TuplePublisher publisher;
   private final Subscriber subscriber;
   private final Origin origin;
   private final String remote;
   
   public PersistentStoreBuilder(Subscriber subscriber, Catalog catalog, Origin origin, String remote) {
      this.publisher = new GridPublisher(catalog);
      this.subscriber = subscriber;
      this.remote = remote;
      this.origin = origin;
   }

   public TupleStore create(PersistentEntityStore store, String[] key, String name) {
      TupleListener listener = new PersistentListener(store, key, name);
      Map<String, String> predicates = new LinkedHashMap<>();
      Query query = new Query(origin, predicates);
      
      if(Objects.nonNull(remote) && !remote.isEmpty()) {
         predicates.put(name, "*");
         subscriber.subscribe(listener, query);
      }
      return new PersistentStore(store, publisher, listener, key, name);
   }
}
