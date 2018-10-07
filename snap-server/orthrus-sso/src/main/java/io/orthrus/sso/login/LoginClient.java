package io.orthrus.sso.login;

import io.orthrus.domain.user.User;
import io.orthrus.sso.mail.MailService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class LoginClient {

   private final WebClient.Builder builder;
   private final MailService service;
   
   public User register(User user, String directory) {
      User registered = builder.baseUrl(directory)
         .build()
         .method(HttpMethod.PUT)
         .uri("/v1/user")
         .accept(MediaType.APPLICATION_JSON)
         .body(BodyInserters.fromObject(user))
         .retrieve()
         .bodyToMono(User.class)
         .retry(4)
         .block();
      
      return registered;
   }
   
   public User login(String email, String directory, String message) {
      User user = builder.baseUrl(directory)
         .build()
         .method(HttpMethod.GET)
         .uri("/v1/user/email/" + email)
         .retrieve()
         .bodyToMono(User.class)
         .retry(4)
         .block();
      
      service.send("admin@orthrus.com", email, "Access Token", message);
      return user;
   }
}
