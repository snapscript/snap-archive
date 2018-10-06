package io.orthrus.directory.user;

import io.orthrus.domain.user.User;
import io.orthrus.store.DataStore;
import io.orthrus.store.DataStoreBuilder;
import io.orthrus.store.DataStoreConfiguration;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataStoreConfiguration.class)
public class UserConfiguration {
   
   private final DataStoreBuilder builder;
   private final File path;
   
   public UserConfiguration(DataStoreBuilder builder, @Value("${directory.store}") File path) {
      this.builder = builder;
      this.path = path;
   }

   @Bean
   public UserDirectory userRepository() {
      DataStore<User> store = builder.create(User.class, path);
      return new UserDirectory(store);
   }
}
