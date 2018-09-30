package io.orthrus.gateway.status;

import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zuooh.http.proxy.plan.ProxyPlan;

@Component
@AllArgsConstructor
public class StatusService {

   private final ProxyPlan plan;

   public List<StatusReport> getStatus() {
      List<StatusReport> report = Lists.newArrayList();
      
      return report;
   }
}
