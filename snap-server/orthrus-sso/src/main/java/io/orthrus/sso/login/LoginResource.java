package io.orthrus.sso.login;

import io.orthrus.domain.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = LoginResource.RESOURCE_PATH)
@Api(value = LoginResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class LoginResource {
   
   public static final String RESOURCE_PATH = "/v1/login";
   public static final String RESOURCE_NAME = "login";
   
   private final LoginService service;
   
   @Inject
   public LoginResource(@Context LoginService service) {
      this.service = service;
   }
   
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Add a user")
   public Response grantAccess(
         @QueryParam("email") String email,
         @QueryParam("redirect") String redirect,
         @QueryParam("remember") Boolean remember,
         @QueryParam("type") String type) 
   {
      URI address = URI.create(redirect);
      User user = service.login(email, redirect, type);
      
      return Response.temporaryRedirect(address).entity(user).build();
   }
   
}
