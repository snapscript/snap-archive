package io.orthrus.directory;

import io.orthrus.rest.EnableResourceServer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableResourceServer
@SpringBootApplication
public class DirectoryApplication {

   public static void main(String[] list) throws Exception {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(DirectoryApplication.class);
      builder.web(WebApplicationType.NONE).run(list);
   }
}
