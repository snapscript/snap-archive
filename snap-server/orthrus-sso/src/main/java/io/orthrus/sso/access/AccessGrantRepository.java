package io.orthrus.sso.access;

import io.orthrus.store.DataStore;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessGrantRepository {

   private final DataStore<AccessGrant> store;
   
   public void save(AccessGrant grant) {
      store.save(grant);
   }
   
   public void deleteByCookie(String cookie){
      store.delete("cookie", cookie);
   }

   public AccessGrant findByCookie(String cookie){
      return store.find("cookie", cookie);
   }
  
   public List<AccessGrant> findAll() {
      return store.findAll();
   }
}
