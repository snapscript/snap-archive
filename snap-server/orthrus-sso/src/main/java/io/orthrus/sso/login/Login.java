package io.orthrus.sso.login;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {

   private final LoginType type;
   private final URI redirect;
   private final String message;
   private final boolean mail;
}
