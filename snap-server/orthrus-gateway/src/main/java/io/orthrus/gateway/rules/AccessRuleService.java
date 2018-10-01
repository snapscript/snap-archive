package io.orthrus.gateway.rules;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zuooh.http.proxy.plan.ProxyFirewallRule;
import com.zuooh.http.proxy.plan.ProxyPlan;

@Component
@AllArgsConstructor
public class AccessRuleService {

   private final ProxyPlan plan;

   @SneakyThrows
   public List<AccessRule> rules() {
      List<AccessRule> results = Lists.newArrayList();
      Set<ProxyFirewallRule> rules = plan.getRules();
      
      for(ProxyFirewallRule rule : rules) {
         String host = rule.getHost();
         String address = rule.getAddress();
         String type = rule.getType();
         int port = rule.getPort();
         AccessRule result = AccessRule.builder()
               .type(type)
               .host(host)
               .address(address)
               .port(port)
               .build();
            
         results.add(result);
      }      
      return results;
   }
}
