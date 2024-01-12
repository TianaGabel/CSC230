package com.gamingroom.gameauth.auth;

import io.dropwizard.auth.Authorizer;

public class GameAuthorizer implements Authorizer<GameUser> 
{
    @Override
    public boolean authorize(GameUser user, String role) {
    	// TG: This code takes in a GameUser and checks that it's role is the given role (ex:USER or ADMIN)
    	return (user.getRoles() != null) && (user.getRoles().contains(role));
    }
}