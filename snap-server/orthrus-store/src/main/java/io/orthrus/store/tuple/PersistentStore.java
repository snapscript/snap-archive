package io.orthrus.store.tuple;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import lombok.AllArgsConstructor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuooh.tuple.Tuple;
import com.zuooh.tuple.TupleListener;

@AllArgsConstructor
class PersistentStore implements TupleStore {

   public static final String ANNOTATION_VERSION = "@version";
   public static final String ANNOTATION_TIME = "@time";
   public static final String ANNOTATION_SOURCE = "@source";
   
   private final PersistentEntityStore store;
   private final TupleListener listener;
   private final String[] key;
   private final String name;

   @Override
   public void save(Tuple tuple) {
      listener.onUpdate(tuple);
   }

   @Override
   public boolean delete(Comparable<?> value) {
      return delete(key[0], value);
   }

   @Override
   public boolean delete(String name, Comparable<?> value) {
      return store.computeInTransaction((transaction) -> {
         EntityIterable iterable = transaction.find(name, key[0], value);
         
         for(Entity entity : iterable) {
            entity.delete();
         }
         return true;
      });
   }
   
   @Override
   public boolean deleteAll(String column, Comparable<?> value) {
      return store.computeInTransaction((transaction) -> {
         EntityIterable iterable = transaction.find(name, column, value);
         
         for(Entity entity : iterable) {
            entity.delete();
         }
         return true;
      });
   }

   @Override
   public Tuple find(Comparable<?> value) {
      return find(key[0], value);
   }
   
   @Override
   public Tuple find(String column, Comparable<?> value) {
      return store.computeInReadonlyTransaction((transaction) -> {
         EntityIterable iterable = transaction.find(name, key[0], value);
         
         if(!iterable.isEmpty()) {
            Map<String, Object> attributes = Maps.newTreeMap();
            Entity entity = iterable.getFirst();
            List<String> properties = entity.getPropertyNames();
            Long version = (Long)entity.getProperty(ANNOTATION_VERSION);
            
            for(String property : properties) {
               Comparable<?> object = entity.getProperty(property);
               attributes.put(property, object);
            }
            return new Tuple(attributes, name, version);
         }
         return null;
      });
   }

   @Override
   public List<Tuple> findAll() {
      return findAll((object) -> true);
   }

   @Override
   public List<Tuple> findAll(Predicate<Tuple> filter) {
      return store.computeInReadonlyTransaction((transaction) -> {
         EntityIterable iterable = transaction.getAll(name);
         
         if(!iterable.isEmpty()) {
            List<Tuple> list = Lists.newArrayList();
         
            for(Entity entity : iterable) {
               Map<String, Object> attributes = Maps.newTreeMap();
               List<String> properties = entity.getPropertyNames();
               Long version = (Long)entity.getProperty(ANNOTATION_VERSION);
               
               for(String property : properties) {
                  Comparable<?> object = entity.getProperty(property);
                  attributes.put(property, object);
               }
               Tuple tuple = new Tuple(attributes, name, version);
               
               if(filter.test(tuple)) {
                  list.add(tuple);
               }
            }
            return list;
         }
         return Collections.emptyList();
      });
   }

   @Override
   public List<Tuple> findAll(String column, Comparable<?> value) {
      return store.computeInReadonlyTransaction((transaction) -> {
         EntityIterable iterable = transaction.find(name, column, value);
         
         if(!iterable.isEmpty()) {
            List<Tuple> list = Lists.newArrayList();
         
            for(Entity entity : iterable) {
               Map<String, Object> attributes = Maps.newLinkedHashMap();
               List<String> properties = entity.getPropertyNames();
               Long version = (Long)entity.getProperty(ANNOTATION_VERSION);
               
               for(String property : properties) {
                  Comparable<?> object = entity.getProperty(property);
                  attributes.put(property, object);
               }
               Tuple tuple = new Tuple(attributes, name, version);
               
               list.add(tuple);
            }
            return list;
         }
         return Collections.emptyList();
      });
   }
}