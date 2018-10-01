package io.orthrus.gateway.status;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zuooh.http.proxy.balancer.connect.ConnectionPoolConnector;
import com.zuooh.http.proxy.balancer.status.StatusMonitor;
import com.zuooh.http.proxy.balancer.status.StatusReport;
import com.zuooh.http.proxy.core.State;
import com.zuooh.http.proxy.plan.ProxyEndPoint;
import com.zuooh.http.proxy.plan.ProxyPlan;

@Component
@AllArgsConstructor
public class StatusService {

   private final ProxyPlan plan;

   @SneakyThrows
   public List<StatusResult> status(Predicate<State> filter) {
      List<StatusResult> results = Lists.newArrayList();
      Set<ProxyEndPoint> servers = plan.getServers();
      
      for(ProxyEndPoint entry : servers) {
         ConnectionPoolConnector connector = entry.getConnector();
         State state = connector.getState();
         
         if(filter.test(state)) {
            StatusResult.ConnectionPool pool = StatusResult.ConnectionPool.builder()
                  .state(state)
                  .active(connector.getActiveCount())
                  .failures(connector.getFailureCount())
                  .idle(connector.getIdleCount())
                  .alive(connector.isAlive())
                  .age(connector.getElapsedTime())
                  .build();
            
            StatusResult result = StatusResult.builder()
                  .pool(pool)
                  .address(String.valueOf(entry.getAddress()))
                  .patterns(entry.getPatterns())
                  .build();
            
            results.add(result);
         }
      }
      return results;
   }
   
   @SneakyThrows
   public List<StatusResult> statusComplete(Predicate<State> filter) {
      List<StatusResult> results = Lists.newArrayList();
      Set<ProxyEndPoint> servers = plan.getServers();
      
      for(ProxyEndPoint entry : servers) {
         ConnectionPoolConnector connector = entry.getConnector();
         State state = connector.getState();
         
         if(filter.test(state)) {
            StatusResult.ConnectionPool pool = StatusResult.ConnectionPool.builder()
                  .state(state)
                  .active(connector.getActiveCount())
                  .failures(connector.getFailureCount())
                  .idle(connector.getIdleCount())
                  .alive(connector.isAlive())
                  .age(connector.getElapsedTime())
                  .build();
            
            StatusMonitor monitor = entry.getMonitor();
            StatusReport report = monitor.checkStatus();                  
            StatusResult result = StatusResult.builder()
                  .pool(pool)
                  .state(report.getState())
                  .local(String.valueOf(report.getLocal()))
                  .remote(String.valueOf(report.getRemote()))
                  .address(String.valueOf(entry.getAddress()))
                  .patterns(entry.getPatterns())
                  .build();
            
            results.add(result);
         }
      }
      return results;
   }
}
