package io.orthrus.sso.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zuooh.common.mail.MailClient;
import com.zuooh.common.mail.MailSender;

@Configuration
@ComponentScan(basePackageClasses = MailConfiguration.class)
public class MailConfiguration {

   private final String host;
   
   public MailConfiguration(@Value("${mail.host}") String host) {
      this.host = host;
   }
   
   @Bean
   public MailClient mailClient() {
      Properties properties = new Properties();
      
      properties.setProperty("mail.smtp.host", host);
      
      return new MailSender(properties);
   }
}
