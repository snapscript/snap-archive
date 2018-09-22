package io.orthrus.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

   private String name;
   private String mail;
}
