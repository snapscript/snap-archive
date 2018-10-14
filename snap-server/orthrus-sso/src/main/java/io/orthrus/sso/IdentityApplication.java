package io.orthrus.sso;

import io.orthrus.rest.EnableResourceServer;
import io.orthrus.sso.access.EnableAccessControl;
import io.orthrus.sso.login.EnableLogin;
import io.orthrus.store.EnableDataStore;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableLogin
@EnableDataStore
@EnableAccessControl
@EnableResourceServer
@SpringBootApplication
public class IdentityApplication {

   public static void main(String[] list) throws Exception {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(IdentityApplication.class);
      builder.web(WebApplicationType.NONE).run(list);
   }
}
