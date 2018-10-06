package io.orthrus.directory.user;

import io.orthrus.domain.user.User;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService {

   private final UserDirectory directory;

   public void save(User user) {
      directory.save(user);
   }
   
   public User findByEmail(String email) {
      return directory.findByEmail(email);
   }
   
   public User findByGuid(String guid) {
      return directory.findByGuid(guid);
   }
}
