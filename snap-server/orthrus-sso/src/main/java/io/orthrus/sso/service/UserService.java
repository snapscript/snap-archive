package io.orthrus.sso.service;

import io.orthrus.domain.User;

import org.springframework.stereotype.Component;

@Component
public class UserService {

   public User getUser() {
      return User.builder().name("name").mail("mail@yahoo.com").build();
   }
}
