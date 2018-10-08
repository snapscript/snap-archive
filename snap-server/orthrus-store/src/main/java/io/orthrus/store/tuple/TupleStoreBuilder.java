package io.orthrus.store.tuple;

import io.orthrus.store.Schema;
import io.orthrus.store.SchemaCompiler;

import java.io.File;

import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;

import com.zuooh.tuple.grid.Catalog;
import com.zuooh.tuple.grid.Grid;
import com.zuooh.tuple.grid.Structure;
import com.zuooh.tuple.query.Origin;
import com.zuooh.tuple.subscribe.Subscriber;

public class TupleStoreBuilder {
   
   private final PersistentStoreBuilder builder;
   private final SchemaCompiler compiler;
   private final Catalog catalog;

   public TupleStoreBuilder(Subscriber subscriber, Catalog catalog, Origin origin) {
      this(subscriber, catalog, origin, null);
   }
   
   public TupleStoreBuilder(Subscriber subscriber, Catalog catalog, Origin origin, String remote) {
      this.builder = new PersistentStoreBuilder(subscriber, catalog, origin, remote);
      this.compiler = new SchemaCompiler();
      this.catalog = catalog;
   }
   
   public TupleStore create(Class<?> type, File path) {
      PersistentEntityStore store = PersistentEntityStores.newInstance(path);
      Schema<?> schema = compiler.compile(type);
      Structure structure = schema.getStructure();
      String[] key = structure.getKey();
      String name = schema.getEntity();
      Grid grid = catalog.getGrid(name);
      
      if(grid == null) {
         throw new IllegalArgumentException("Grid not found for " + type);
      }
      if(!path.exists()) {
         path.mkdirs();
      }
      return builder.create(store, key, name);
   }
}
