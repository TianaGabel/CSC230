package com.gamingroom.gameauth.controller;
 
import io.dropwizard.auth.Auth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.gamingroom.gameauth.auth.GameUser;
import com.gamingroom.gameauth.dao.GameUserDB;
import com.gamingroom.gameauth.representations.GameUserInfo;

//TG: Added path annotation
@Path("/gameusers")
@Produces(MediaType.APPLICATION_JSON)
public class GameUserRESTController {
 
    private final Validator validator;
 
    public GameUserRESTController(Validator validator) {
        this.validator = validator;
    }
 
    @PermitAll
    @GET
    public Response getGameUsers(@Auth GameUser user) {
        return Response.ok(GameUserDB.getGameUsers()).build();
    }
 
    // TG: this retrieves user based on ID
    @GET
    @Path("/{id}")
    @RolesAllowed({"User"})
    public Response getGameUserById(@PathParam("id") Integer id,@Auth GameUser user) {
        GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
        if (gameUserInfo != null)
            return Response.ok(gameUserInfo).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }


    // TG: This creates a new game user
    @POST
    @RolesAllowed({"Admin"})
    public Response createGameUser(GameUserInfo gameUserInfo, @Auth GameUser user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
        GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
        //Case there is a client-side error
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<GameUserInfo> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        //Case successful update
        if (e != null) {
            GameUserDB.updateGameUser(gameUserInfo.getId(), gameUserInfo);
            return Response.created(new URI("/gameusers/" + gameUserInfo.getId())).build();
        //Case GameUserInfo does not exist
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    //This updates game user, can only be done with matching GameUserInfo
    @PUT
    @Path("/{id}")
    public Response updateGameUserById(@PathParam("id") Integer id, GameUserInfo gameUserInfo) {
        //Validation
        Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
        GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
        //Case there is a client side error
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<GameUserInfo> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        //Case successful put
        if (e != null) {
            gameUserInfo.setId(id);
            GameUserDB.updateGameUser(id, gameUserInfo);
            return Response.ok(gameUserInfo).build();
        //Case GameUserInfo does not exist
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response removeGameUserById(@PathParam("id") Integer id) {
        GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
        //Case user deleted
        if (gameUserInfo != null) {
            GameUserDB.removeGameUser(id);
            return Response.ok().build();
        //Case user doesn't exist
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}