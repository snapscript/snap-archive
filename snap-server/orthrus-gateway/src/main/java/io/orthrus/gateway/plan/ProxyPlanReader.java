package io.orthrus.gateway.plan;

import io.orthrus.common.ClassPathReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import lombok.AllArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.PropertyPlaceholderHelper;

@AllArgsConstructor
public class ProxyPlanReader {

   private final PropertyPlaceholderHelper helper;
   private final PropertyResolver resolver;

   public ProxyPlanReader(PropertyResolver resolver) {
      this.helper = new PropertyPlaceholderHelper("${", "}");
      this.resolver = resolver;
   }
   
   public Reader readPlan(String plan) throws IOException {
      String text = readFile(plan);
      
      if(text != null) {
         String result = helper.replacePlaceholders(text, (name) -> {
            return resolver.getProperty(name);
         });
         return new StringReader(result);
      }
      return null;
   }
   
   private String readFile(String path) throws IOException {
      InputStream stream = ClassPathReader.getInputStream(path);
      
      if(stream == null) {
         File file = new File(path);
         FileReader reader = new FileReader(file);
         
         return IOUtils.toString(reader);
      }
      return IOUtils.toString(stream, StandardCharsets.UTF_8);
   }
}
