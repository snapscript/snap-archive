package io.orthrus.sso.login;

import io.orthrus.sso.access.AccessRequest;

public enum LoginType {
   OTP {
      @Override
      public String generateMail(AccessRequest request, String base) {
         int code = request.getCode();
         String address = base + "/login/access-code";
         
         return "Please enter " + code + " to " + address;
      }
   },
   TOKEN {
      @Override
      public String generateMail(AccessRequest request, String base) {
         String token = request.getToken();
         String address = base + "/login/grant/" + token;
         
         return "Please click " + address;
      }
   };
  
   public abstract String generateMail(AccessRequest request, String base);

   public static LoginType resolveType(String token) {
      if(token != null) {
         LoginType[] types = LoginType.values();
         
         for(LoginType type : types) {
            String name = type.name();
            
            if(name.equalsIgnoreCase(token)) {
               return type;
            }
         }
      }
      return TOKEN;
   }
}
