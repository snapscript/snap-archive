package io.orthrus.sso.access;

import io.orthrus.store.DataStore;
import io.orthrus.store.DataStoreBuilder;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AccessConfiguration.class)
public class AccessConfiguration {

   private final File requests;
   private final File grants;
   
   public AccessConfiguration(
         @Value("${login.access-request.store}") File requests,
         @Value("${login.access-grant.store}") File grants)
   {
      this.requests = requests;
      this.grants = grants;
   }
   
   @Bean      
   public AccessRequestRepository accessRequestRepository(DataStoreBuilder builder) {
      DataStore<AccessRequest> store = builder.create(AccessRequest.class, requests);
      return new AccessRequestRepository(store);
   }
   
   @Bean      
   public AccessGrantRepository accessGrantRepository(DataStoreBuilder builder) {
      DataStore<AccessGrant> store = builder.create(AccessGrant.class, grants);
      return new AccessGrantRepository(store);
   }
}
