package io.orthrus.directory.user;

import io.orthrus.domain.user.User;
import io.orthrus.store.DataStore;
import io.orthrus.store.DataStoreBuilder;
import io.orthrus.store.DataStoreConfiguration;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AllArgsConstructor
@Import(DataStoreConfiguration.class)
public class UserConfiguration {
   
   private final DataStoreBuilder builder;

   @Bean
   public UserDirectory userRepository() {
      DataStore<User> store = builder.create(User.class);
      return new UserDirectory(store);
   }
}
