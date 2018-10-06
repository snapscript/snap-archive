package io.orthrus.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Schema<T> {
   
   private final Class<T> type;
   private final String entity;
   private final String key;
}
