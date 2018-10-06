package io.orthrus.sso.access;

import io.orthrus.store.DataStore;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessRequestRepository {
   
   private final DataStore<AccessRequest> store;
   
   public void save(AccessRequest request) {
      store.save(request);
   }
   
   public void deleteByToken(String token){
      store.deleteFirst("token", token);
   }
   
   public void deleteByCode(int code){
      store.deleteFirst("code", code);
   }
   
   public AccessRequest findByToken(String token){
      return store.findFirst("token", token);
   }
   
   public AccessRequest findByCode(int code){
      return store.findFirst("code", code);
   }
  
   public List<AccessRequest> findAll() {
      return store.findAll();
   }
}
