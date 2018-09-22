package io.orthrus.rest.container;

import io.orthrus.common.AnnotationPresentScanner;
import io.orthrus.common.ClassPathScanner;
import io.orthrus.rest.swagger.SwaggerResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import java.util.Set;

import javax.ws.rs.Path;

import lombok.SneakyThrows;

public class ResourceScanner {

   private static String RESOURCE_SUFFIX = "Resource";
   
   private final ClassPathScanner scanner;
   
   public ResourceScanner(String... packages) {
      this.scanner = new AnnotationPresentScanner(Path.class, RESOURCE_SUFFIX, packages);
   }
   
   @SneakyThrows
   public Set<Class<?>> scan(boolean swagger) {
      Set<Class<?>> types = scanner.scan();
      
      if(swagger) {
         types.add(SwaggerResource.class);
         types.add(SwaggerSerializers.class);
      } else {
         types.remove(SwaggerResource.class);
      }
      return types;
   }
}
