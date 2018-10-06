package io.orthrus.store;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import com.zuooh.message.serialize.AttributeMarshaller;

@AllArgsConstructor
public class RecordBuilder {

   private final AttributeMarshaller marshaller;

   @SneakyThrows
   public <T> Record toRecord(Schema<T> schema, T object) {
      Map<String, Object> attributes = marshaller.fromObject(object);
      return new Record(attributes, schema);
   }
   
   @SneakyThrows
   public <T> T fromRecord(Schema<T> schema, Record record) {
      Map<String, Object> attributes = record.getAttributes();
      return (T)marshaller.toObject(attributes);
   }
   
}
