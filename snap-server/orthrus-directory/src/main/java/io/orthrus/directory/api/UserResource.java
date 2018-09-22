package io.orthrus.directory.api;

import io.orthrus.directory.service.UserService;
import io.orthrus.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;


@Path(value = UserResource.RESOURCE_PATH)
@Api(value = UserResource.RESOURCE_NAME, produces = MediaType.APPLICATION_JSON)
public class UserResource {
   
   public static final String RESOURCE_PATH = "/v1/user";
   public static final String RESOURCE_NAME = "staging";
   
   private final UserService service;
   
   @Inject
   public UserResource(UserService service) {
      this.service = service;
   }
   
   @GET
   @ApiOperation(value = "Get user")
   public User getUser() {
      return service.getUser();
   }

}
