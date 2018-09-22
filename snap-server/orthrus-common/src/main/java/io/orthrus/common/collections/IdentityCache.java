package io.orthrus.common.collections;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class IdentityCache<K, V> implements Cache<K, V> {

   private final Map<K, V> cache;

   public IdentityCache() {
      this(32);
   }

   public IdentityCache(int capacity) {
      this.cache = new IdentityHashMap<K, V>();
   }

   @Override
   public synchronized void clear() {
      cache.clear();
   }

   @Override
   public synchronized int size() {
      return cache.size();
   }

   @Override
   public synchronized Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public synchronized V fetch(K key) {
      return cache.get(key);
   }

   @Override
   public synchronized void cache(K key, V value) {
      cache.put(key, value);
   }

   @Override
   public synchronized V take(K key) {
      return cache.remove(key);
   }

   @Override
   public synchronized boolean contains(K key) {
      return cache.containsKey(key);
   }

   @Override
   public synchronized boolean isEmpty() {
      return cache.isEmpty();
   }
   
   @Override
   public synchronized String toString() {
      return String.valueOf(cache);
   }
}