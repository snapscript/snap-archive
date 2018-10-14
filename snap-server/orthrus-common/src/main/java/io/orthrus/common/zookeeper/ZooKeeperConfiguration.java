package io.orthrus.common.zookeeper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = ZooKeeperConfiguration.class)
public class ZooKeeperConfiguration {

   private final ObjectMapper mapper;
   private final RetryPolicy policy;
   private final String hosts;
   
   public ZooKeeperConfiguration(
         @Value("${zookeeper.hosts}") String hosts,
         @Value("${zookeeper.retries:10}") int retries) 
   {
      this.policy = new ExponentialBackoffRetry(100, retries);
      this.mapper = new ObjectMapper();
      this.hosts = hosts;
   }
   
   @Bean
   @SneakyThrows
   public ZooKeeperClient zooKeeperClient() {
      CuratorFramework framework = CuratorFrameworkFactory.newClient(hosts, policy);
      
      log.info("Connecting to {}", hosts);
      framework.start();
      
      return new ZooKeeperClient(framework, mapper);
   }
}