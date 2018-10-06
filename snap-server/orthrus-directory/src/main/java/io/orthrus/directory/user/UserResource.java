package io.orthrus.directory.user;

import io.orthrus.domain.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path(value = UserResource.RESOURCE_PATH)
@Api(value = UserResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class UserResource {
   
   public static final String RESOURCE_PATH = "/v1/user";
   public static final String RESOURCE_NAME = "user";
   
   private final UserService service;
   
   @Inject
   public UserResource(@Context UserService service) {
      this.service = service;
   }
   
   @PUT
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Add a user")
   public User save(User user) {
      service.save(user);
      return user;
   }
   
   @GET
   @Path("/guid/{guid}")
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Get user by guid")
   public User findByGuid(@PathParam("guid") String guid) {
      return service.findByGuid(guid);
   }
   
   @GET
   @Path("/email/{email}")
   @Produces(MediaType.APPLICATION_JSON)
   @ApiOperation(value = "Get user by email")
   public User findByEmail(@PathParam("email") String email) {
      return service.findByEmail(email);
   }
}
