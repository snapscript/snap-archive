package io.orthrus.store;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Record {

   private final Map<String, Object> attributes;
   private final Schema schema;
}
