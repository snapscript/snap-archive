package io.orthrus.gateway.health;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.zuooh.http.proxy.balancer.status.StatusMonitor;
import com.zuooh.http.proxy.plan.ProxyEndPoint;
import com.zuooh.http.proxy.plan.ProxyPlan;

@Component
@AllArgsConstructor
public class HealthService {
   
   private final ProxyPlan plan;
   
   @SneakyThrows
   public Map<String, String> health() {
      Map<String, String> results = Maps.newHashMap();
      Set<ProxyEndPoint> servers = plan.getServers();
      
      for(ProxyEndPoint entry : servers) {
         StatusMonitor monitor = entry.getMonitor();
         String state = monitor.getLastStatus();
         String address = String.valueOf(entry.getAddress());
         
         results.put(address, state);
      }
      return results;
   }
}
