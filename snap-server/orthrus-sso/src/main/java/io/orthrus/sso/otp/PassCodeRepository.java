package io.orthrus.sso.otp;

import io.orthrus.store.DataStore;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PassCodeRepository {

   private final DataStore<PassCode> store;
   
   public void save(PassCode code) {
      store.save(code);
   }
   
   public void deleteByCode(int code){
      store.deleteFirst("code", code);
   }
   
   public PassCode findByCode(int code){
      return store.findFirst("code", code);
   }
  
   public Iterable<PassCode> findAll() {
      return store.findAll();
   }
}
