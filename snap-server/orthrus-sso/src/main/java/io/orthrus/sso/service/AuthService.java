package io.orthrus.sso.service;

import org.springframework.stereotype.Component;

@Component
public class AuthService {

   public String buildForm(String responseType, String clientId, String redirectUri, String scope, String state) {
      StringBuilder builder = new StringBuilder();
      builder.append("<html>");
      builder.append("<body>");
      builder.append("<form action='/v1/oauth/login' method='POST'>");
      builder.append("User: <input type='text' name='user'><br>");
      builder.append("Password: <input type='password' name='password'><br>");
      builder.append("<input type='submit' value='Login'>");
      builder.append("<input type='hidden' name='response_type' value='" + responseType + "'>");
      builder.append("<input type='hidden' name='client_id' value='" + clientId + "'>");
      builder.append("<input type='hidden' name='redirect_uri' value='" + redirectUri + "'>");
      builder.append("<input type='hidden' name='scope' value='" + scope + "'>");
      builder.append("<input type='hidden' name='state' value='" + state + "'>");
      builder.append("</form>");  
      builder.append("</body>");  
      builder.append("</html>");      
      return builder.toString();
   }
}
