package io.orthrus.store;

import java.lang.reflect.Field;

public class SchemaCompiler {

   public <T> Schema<T> compile(Class<T> type) {
      String entity = type.getSimpleName();
      Class base = type;
   
      while(base != null) {
         Field[] fields = base.getDeclaredFields();
         
         for(Field field : fields) {
            String name = field.getName();
            
            if(field.isAnnotationPresent(PrimaryKey.class)) {
               return new Schema<>(type, entity, name);
            }
         }
      }
      return new Schema<T>(type, entity, null);
   }
}
