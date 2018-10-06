package io.orthrus.directory.user;

import io.orthrus.domain.user.User;
import io.orthrus.store.DataStore;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDirectory {

   private final DataStore<User> store;
   
   public void save(User user) {
      store.save(user);
   }
   
   public User findByEmail(String email) {
      return store.findFirst("email", email);
   }
   
   public User findByGuid(String guid) {
      return store.findFirst("guid", guid);
   }
}
