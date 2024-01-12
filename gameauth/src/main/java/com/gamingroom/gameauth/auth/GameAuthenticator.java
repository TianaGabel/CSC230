package com.gamingroom.gameauth.auth;


import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
 
import java.util.Map;
import java.util.Optional;
import java.util.Set;
 
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
 
public class GameAuthenticator implements Authenticator<BasicCredentials, GameUser> 
{
	
	/* Use cases located in GameUserRESTController
	 * Admin has posting, get and delete privileges
	 * User has get and update privileges
	 */
		
	private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
        "guest", ImmutableSet.of(),
        "user", ImmutableSet.of("USER"),
        "admin", ImmutableSet.of("ADMIN", "USER")
    );
 
	//TG: Authenticate method where the server can refer to credentials for authentication
    @Override
    public Optional<GameUser> authenticate(BasicCredentials credentials) throws AuthenticationException 
    {
    	//The first section checks if the server's username is in VALID_USERS
    	//THe second section checks to see if the password is "password
        if (VALID_USERS.containsKey(credentials.getUsername()) && "password".equals(credentials.getPassword())) 
        {
        	//This creates an optional object(used as a container when null would cause problems)
        	//The Gameuser uses the username from the server, and then gets the permissions from VALID_USER.
        	return Optional.of(new GameUser(credentials.getUsername(),VALID_USERS.get(credentials.getUsername())));
        }
        return Optional.empty();
    }
}
