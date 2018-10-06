package io.orthrus.sso.login;

import io.orthrus.sso.access.AccessService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = LoginConfiguration.class)
public class LoginConfiguration {

   private final String directory;
   private final String base;
   
   public LoginConfiguration(
         @Value("${login.url.directory}") String directory,
         @Value("${login.url.base}") String base)
   {
      this.directory = directory;
      this.base = base;
   }
   
   @Bean
   public LoginService loginService(LoginClient client, AccessService service) {
      return new LoginService(service, client, directory, base);
   }
}
