package io.orthrus.gateway.status;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zuooh.http.proxy.balancer.status.StatusMonitor;
import com.zuooh.http.proxy.balancer.status.StatusReport;
import com.zuooh.http.proxy.core.State;
import com.zuooh.http.proxy.plan.ProxyEndPoint;
import com.zuooh.http.proxy.plan.ProxyPlan;
import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class StatusService {

   private final ProxyPlan plan;

   @SneakyThrows
   public List<StatusResult> status(Predicate<State> filter) {
      List<StatusResult> results = Lists.newArrayList();
      Set<ProxyEndPoint> servers = plan.getServers();
      
      for(ProxyEndPoint entry : servers) {
         StatusMonitor monitor = entry.getMonitor();
         StatusReport report = monitor.checkStatus();
         State state = report.getState();
         
         if(filter.test(state)) {
            List<String> patterns = entry.getPatterns();
            String address = "" +entry.getAddress();
            StatusResult result = StatusResult.builder()
                  .state(state)
                  .address(address)
                  .patterns(patterns)
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
         StatusMonitor monitor = entry.getMonitor();
         StatusReport report = monitor.checkStatus();
         State state = report.getState();
         
         if(filter.test(state)) {
            List<String> patterns = entry.getPatterns();
            String address = "" +entry.getAddress();
            String local = "" + report.getLocal();
            String remote = "" + report.getRemote();
            StatusResult result = StatusResult.builder()
                  .state(state)
                  .local(local)
                  .remote(remote)
                  .address(address)
                  .patterns(patterns)
                  .build();
            
            results.add(result);
         }
      }
      return results;
   }
}
