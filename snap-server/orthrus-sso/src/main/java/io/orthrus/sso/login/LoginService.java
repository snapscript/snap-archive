package io.orthrus.sso.login;

import io.orthrus.domain.user.User;
import io.orthrus.sso.access.AccessRequest;
import io.orthrus.sso.access.AccessService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginService {
   
   private final AccessService service;
   private final LoginClient client;
   private final String directory;
   private final String base;

   public User login(String email, String redirect, String mode) {
      AccessRequest request = service.createRequest(email, redirect);
      LoginType type = LoginType.resolveType(mode);
      String message = type.generateMail(request, base);
      
      return client.login(email, directory, message);
   }
}
