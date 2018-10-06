package io.orthrus.store;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.PersistentEntityStores;
import lombok.AllArgsConstructor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataStoreBuilder {
   
   private final SchemaCompiler compiler;
   private final RecordBuilder builder;
   
   public DataStoreBuilder(RecordBuilder builder) {
      this.compiler = new SchemaCompiler();
      this.builder = builder;
   }

   public <T> DataStore<T> create(Class<T> type, File path) {
      PersistentEntityStore store = PersistentEntityStores.newInstance(path);
      Schema schema = compiler.compile(type);
      
      if(!path.exists()) {
         path.mkdirs();
      }
      return new PersistentDataStore<>(store, builder, schema);
   }
   
   @AllArgsConstructor
   private static class PersistentDataStore<T> implements DataStore<T> {
      
      private final PersistentEntityStore store;
      private final RecordBuilder builder;
      private final Schema<T> schema;

      @Override
      public void save(T object) {
         String key = schema.getKey();
         String name = schema.getEntity();
         Record record = builder.toRecord(schema, object);
         
         store.computeInTransaction((transaction) -> {
            Map<String, Object> attributes = record.getAttributes();
            
            if(key != null) {
               Comparable value = (Comparable)attributes.get(key);
               EntityIterable iterable = transaction.find(name, key, value);
               
               for(Entity entity : iterable) {
                  entity.delete();
               }
            }
            Entity entity = transaction.newEntity(name);
            Set<Map.Entry<String, Object>> entries = attributes.entrySet();
            
            for(Map.Entry<String, Object> entry : entries) {
               String column = entry.getKey();
               Comparable value = (Comparable)entry.getValue();
               
               if(value == null){
                  if(Objects.equals(column, key)) {
                     throw new IllegalStateException("Primary key is null");
                  }
               } else {
                  entity.setProperty(column, value);
               }
            }
            return entity.getId();
         });
      }

      @Override
      public boolean delete(Comparable value) {
         String key = schema.getKey();
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, key, value);
            
            for(Entity entity : iterable) {
               entity.delete();
            }
            return true;
         });
      }

      @Override
      public boolean deleteFirst(String column, Comparable value) {
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, column, value);
            
            if(iterable.isEmpty()) {
               Entity entity = iterable.getFirst();
               return entity.delete();
            }
            return false;
         });
      }

      @Override
      public boolean deleteAll(String column, Comparable value) {
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, column, value);
            
            for(Entity entity : iterable) {
               entity.delete();
            }
            return true;
         });
      }

      @Override
      public T find(Comparable value) {
         String key = schema.getKey();
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, key, value);
            
            if(!iterable.isEmpty()) {
               Map<String, Object> attributes = Maps.newLinkedHashMap();
               Entity entity = iterable.getFirst();
               List<String> properties = entity.getPropertyNames();
               
               for(String property : properties) {
                  Comparable object = entity.getProperty(property);
                  attributes.put(property, object);
               }
               Record record = Record.builder()
                        .attributes(attributes)
                        .schema(schema)
                        .build();
            
               return builder.fromRecord(schema, record);
            }
            return null;
         });
      }

      @Override
      public T findFirst(String column, Comparable value) {
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, column, value);
            
            if(!iterable.isEmpty()) {
               Map<String, Object> attributes = Maps.newLinkedHashMap();
               Entity entity = iterable.getFirst();
               List<String> properties = entity.getPropertyNames();
            
               for(String property : properties) {
                  Comparable object = entity.getProperty(property);
                  attributes.put(property, object);
               }
               Record record = Record.builder()
                        .attributes(attributes)
                        .schema(schema)
                        .build();
            
               return builder.fromRecord(schema, record);
            }
            return null;
         });
      }

      @Override
      public List<T> findAll() {
         return findAll((object) -> true);
      }

      @Override
      public List<T> findAll(Predicate<T> filter) {
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.getAll(name);
            
            if(!iterable.isEmpty()) {
               Map<String, Object> attributes = Maps.newLinkedHashMap();
               List<T> list = Lists.newArrayList();
            
               for(Entity entity : iterable) {
                  List<String> properties = entity.getPropertyNames();
                  
                  for(String property : properties) {
                     Comparable object = entity.getProperty(property);
                     attributes.put(property, object);
                  }
                  Record record = Record.builder()
                           .attributes(attributes)
                           .schema(schema)
                           .build();
               
                  T object = builder.fromRecord(schema, record);
                  
                  if(filter.test(object)) {
                     list.add(object);
                  }
                  attributes.clear();
               }
               return list;
            }
            return Collections.emptyList();
         });
      }

      @Override
      public List<T> findAll(String column, Comparable value) {
         String name = schema.getEntity();
         
         return store.computeInTransaction((transaction) -> {
            EntityIterable iterable = transaction.find(name, column, value);
            
            if(!iterable.isEmpty()) {
               Map<String, Object> attributes = Maps.newLinkedHashMap();
               List<T> list = Lists.newArrayList();
            
               for(Entity entity : iterable) {
                  List<String> properties = entity.getPropertyNames();
                  
                  for(String property : properties) {
                     Comparable object = entity.getProperty(property);
                     attributes.put(property, object);
                  }
                  Record record = Record.builder()
                           .attributes(attributes)
                           .schema(schema)
                           .build();
               
                  T object = builder.fromRecord(schema, record);
                  
                  list.add(object);
                  attributes.clear();
               }
               return list;
            }
            return Collections.emptyList();
         });
      }
      
   }
}
