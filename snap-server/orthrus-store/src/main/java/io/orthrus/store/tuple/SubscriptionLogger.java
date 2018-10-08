package io.orthrus.store.tuple;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.zuooh.tuple.Tuple;
import com.zuooh.tuple.query.Query;
import com.zuooh.tuple.subscribe.SubscriptionAdapter;

@Slf4j
@Component
public class SubscriptionLogger extends SubscriptionAdapter {

   @Override
   public void onConnect(String address) {
      log.info("Connection established for " + address);
   }

   @Override
   public void onClose(String address) {
      log.info("Connection closed for " + address);
   }

   @Override
   public void onException(String address, Exception cause) {
      log.info("Failure for " + address, cause);
   }

   @Override
   public void onSubscribe(String address, Query query) {
      log.info("Subscription for " + address + " updated to " + query);
   }   

   @Override
   public void onUpdate(String address, Tuple tuple) {
      log.info("Tuple for " + address + " was " + tuple);
   }   

   @Override
   public void onHeartbeat(String address) {
      log.info("Heartbeat for " + address);
   }
}
