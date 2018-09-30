package io.orthrus.sso.api;

import java.net.URI;

import io.orthrus.sso.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(value = AuthResource.RESOURCE_PATH)
@Api(value = AuthResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class AuthResource {
   
   public static final String RESOURCE_PATH = "/v1/oauth";
   public static final String RESOURCE_NAME = "auth";
   
   private final AuthService service;
   
   @Inject
   public AuthResource(AuthService service) {
      this.service = service;
   }

   // http://localhost:7979/v1/auth?response_type=code&client_id=CLIENT_ID&redirect_uri=REDIRECT_URI&scope=photos&state=1234zyx
   @GET
   @Path("/auth")
   @Produces(MediaType.TEXT_HTML)
   @ApiOperation(value = "Start login process")
   public Response startLogin(
         @QueryParam("response_type") String responseType,
         @QueryParam("client_id") String clientId,
         @QueryParam("redirect_uri") String redirectUri,
         @QueryParam("scope") String scope,
         @QueryParam("state") String state) 
   {
      String text = service.buildForm(responseType, clientId, redirectUri, scope, state);
      return Response.ok(text).build();
   }
   
   @POST
   @Path("/login")
   @Produces(MediaType.TEXT_HTML)
   @ApiOperation(value = "Perform login")
   public Response performLogin(
         @QueryParam("response_type") String responseType,
         @QueryParam("client_id") String clientId,
         @QueryParam("redirect_uri") String redirectUri,
         @QueryParam("scope") String scope,
         @QueryParam("state") String state,
         @QueryParam("user") String user,
         @QueryParam("password") String password) 
   {
      URI location = URI.create(redirectUri + "?access_code=1234");
      return Response.temporaryRedirect(location).build();
   }
}
