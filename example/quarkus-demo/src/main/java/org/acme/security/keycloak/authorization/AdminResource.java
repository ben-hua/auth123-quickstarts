package org.acme.security.keycloak.authorization;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/admin")
@Authenticated
public class AdminResource {

    @GET
    public String manage() {
        return "granted";
    }
}
