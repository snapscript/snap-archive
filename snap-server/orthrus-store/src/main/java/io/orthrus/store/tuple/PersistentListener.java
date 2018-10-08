package io.orthrus.store.tuple;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityIterable;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import lombok.AllArgsConstructor;

import com.zuooh.tuple.Tuple;
import com.zuooh.tuple.TupleAdapter;

@AllArgsConstructor
class PersistentListener extends TupleAdapter {
   
   private final PersistentEntityStore store;
   private final String[] key;
   private final String name;

   @Override
   public void onUpdate(Tuple tuple) {
      store.computeInTransaction((transaction) -> {
         Map<String, Object> attributes = tuple.getAttributes();
         
         if(key != null) {
            Comparable<?> value = (Comparable<?>)attributes.get(key[0]);
            EntityIterable iterable = transaction.find(name, key[0], value);
            
            for(Entity entity : iterable) {
               entity.delete();
            }
         }
         Entity entity = transaction.newEntity(name);
         Set<Map.Entry<String, Object>> entries = attributes.entrySet();
         
         for(Map.Entry<String, Object> entry : entries) {
            String column = entry.getKey();
            Comparable<?> value = (Comparable<?>)entry.getValue();
            
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
}
