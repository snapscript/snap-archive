package io.orthrus.store;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zuooh.attribute.AttributeSerializer;
import com.zuooh.attribute.CombinationBuilder;
import com.zuooh.attribute.ObjectBuilder;
import com.zuooh.attribute.ReflectionBuilder;
import com.zuooh.attribute.SerializationBuilder;
import com.zuooh.message.serialize.AttributeMarshaller;

@Configuration
@ComponentScan(basePackageClasses = DataStoreConfiguration.class)
public class DataStoreConfiguration {

   @Bean
   public DataStoreBuilder storeBuilder() {
      Set<ObjectBuilder> sequence = new LinkedHashSet<ObjectBuilder>();
      ObjectBuilder reflection = new ReflectionBuilder();
      ObjectBuilder serialization = new SerializationBuilder();
      ObjectBuilder combination = new CombinationBuilder(sequence);
      AttributeSerializer serializer = new AttributeSerializer(combination);
      AttributeMarshaller marshaller = new AttributeMarshaller(serializer);
      RecordBuilder builder = new RecordBuilder(marshaller);

      sequence.add(reflection);
      sequence.add(serialization);

      return new DataStoreBuilder(builder);
   }
}
