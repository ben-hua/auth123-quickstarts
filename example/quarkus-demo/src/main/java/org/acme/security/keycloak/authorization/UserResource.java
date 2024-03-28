package org.acme.security.keycloak.authorization;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.Permission;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/api/users")
public class UserResource {

    @Inject
    SecurityIdentity identity;

    @Inject
    AuthzClient authzClient;

    @GET
    @Path("/me")
    public User me() {
        return new User(identity);
    }

    @Produces("application/json")
    @Path("/permissions")
    @GET
    public List<String> getPermissions() {
        String accessToken = identity.getCredential(AccessTokenCredential.class).getToken();
        String rpt = authzClient.authorization(accessToken).authorize().getToken();
        List<Permission> permissions = authzClient.protection().introspectRequestingPartyToken(rpt).getPermissions();

        return permissions.stream().mapMulti((Permission e, Consumer<String> c) -> {
            c.accept(e.getResourceName());

            if (e.getScopes() != null && !e.getScopes().isEmpty())
                e.getScopes().forEach(scope -> c.accept(e.getResourceName() + ":" + scope));

        }).collect(Collectors.toList());
    }

    public static class User {

        private final String userName;

        User(SecurityIdentity securityContext) {
            this.userName = securityContext.getPrincipal().getName();
        }

        public String getUserName() {
            return userName;
        }
    }
}
