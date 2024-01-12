package com.gamingroom.gameauth.controller;
 
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gamingroom.gameauth.representations.GameUserInfo;
 
@Produces(MediaType.TEXT_PLAIN)
@Path("/client/")
public class RESTClientController 
{
    private Client client;
 
    public RESTClientController(Client client) {
        this.client = client;
    }

    //TG: fetches string of all users
    @GET
    @Path("/gameusers/")
    public String getGameUsers()
    {
       //Hard coding this in your application can cause security issues
        WebTarget webTarget = client.target("http://localhost:8080/gameusers");
      //JSON data is added to the invocation builder
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON); 
      //the metadata is passed to a response that will hold it
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
      //the response object reads the metadata into an Arraylist
        ArrayList gameusers = response.readEntity(ArrayList.class);
        return gameusers.toString();
    }
     
    //TG: fetches id user info
    @GET
    @Path("/gameusers/{id}")
    public String getGameUserById(@PathParam("id") int id)
    {
        //Do not hard code
        WebTarget webTarget = client.target("http://localhost:8080/gameusers/"+id);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON); 
        Response response = invocationBuilder.get();
      //Because we specified the /{id} the meta data is different, suppress warnings is not necessary
        GameUserInfo gameUserInfo = response.readEntity(GameUserInfo.class);
        return gameUserInfo.toString();
    }
}