package io.orthrus.gateway.plan;

import java.io.Reader;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;

import com.zuooh.http.proxy.plan.ProxyPlan;
import com.zuooh.http.proxy.plan.legacy.ProxyPlanParser;

@Configuration
@ComponentScan(basePackageClasses = ProxyPlanConfiguration.class)
public class ProxyPlanConfiguration {

   private final ProxyTraceLogger logger;
   private final ProxyPlanReader reader;
   private final ProxyPlanParser parser;
   private final String path;
   
   public ProxyPlanConfiguration(
         PropertyResolver resolver, 
         @Value("${gateway.plan}") String path,
         @Value("${gateway.debug:false}") boolean debug) 
   {
      this.reader = new ProxyPlanReader(resolver);
      this.logger = new ProxyTraceLogger(debug);
      this.parser = new ProxyPlanParser();
      this.path = path;
   }
   
   @Bean
   @SneakyThrows
   public ProxyPlan proxyPlan() {
      Reader plan = reader.readPlan(path);
      return parser.parse(plan, logger);
   }
}
