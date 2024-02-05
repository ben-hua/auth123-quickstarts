package org.acme.security.keycloak.authorization;

import java.time.LocalDate;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/public")
public class PublicResource {

    @GET
    public String serve() {
        return "Hello from a public resource!";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Merchant Json() {
        return new Merchant("hellow world to json");
    }

}

record Merchant(String name){}
