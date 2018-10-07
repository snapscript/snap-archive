package io.orthrus.sso.login;

import io.orthrus.sso.access.AccessRequest;

import java.net.URI;

public enum LoginType {
   OTP {
      @Override
      public Login generateLogin(AccessRequest request, String base) {
         int code = request.getCode();
         String address = String.format("%s/login/access-code", base);
         String normal = address.replace("//login/", "/login/");
         
         return new Login(this, null, "Please enter " + code + " to " + normal, true);
      }
   },
   TOKEN {
      @Override
      public Login generateLogin(AccessRequest request, String base) {
         String token = request.getToken();
         String address = String.format("%s/login/access/grant/%s", base, token);
         String normal = address.replace("//login/", "/login/");
         
         return new Login(this, null, "Please click " + normal, true);
      }
   },
   PASSWORD {
      @Override
      public Login generateLogin(AccessRequest request, String base) {
         String token = request.getToken();
         String address = String.format("%s/login/access/grant/%s", base, token);
         String normal = address.replace("//login/", "/login/");
         URI redirect = URI.create(normal);
         
         return new Login(this, redirect, address, false);
      }
   };
  
   public abstract Login generateLogin(AccessRequest request, String base);

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
