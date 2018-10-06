package io.orthrus.gateway;

import io.orthrus.gateway.plan.EnableProxyPlan;
import io.orthrus.jmx.EnableManagement;
import io.orthrus.rest.EnableResourceServer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableManagement
@EnableProxyPlan
@EnableResourceServer
@SpringBootApplication
public class GatewayApplication {

   public static void main(String[] list) throws Exception {
      SpringApplicationBuilder builder = new SpringApplicationBuilder(GatewayApplication.class);
      builder.web(WebApplicationType.NONE).run(list);
   }
}
