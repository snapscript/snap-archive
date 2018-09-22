package io.orthrus.server.rest.container;

import java.util.Set;

import javax.ws.rs.Path;

public class ResourceScanner {

   private static String RESOURCE_SUFFIX = "Resource";
   
   private final ClassPathScanner scanner;
   
   public ResourceScanner(String... packages) {
      this.scanner = new AnnotationPresentScanner(Path.class, RESOURCE_SUFFIX, packages);
   }
   
   public Set<Class<?>> scan(boolean swagger) {
      Set<Class<?>> resources = scanner.scan();
      
      if(swagger) {
         resources.add(SwaggerResource.class);
         resources.add(SwaggerSerializers.class);
      } else {
         resources.remove(SwaggerResource.class);
      }
   }
}
