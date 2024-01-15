package com.auth123.quickstarts.springdemo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoResourceController {
    @GetMapping("/api/users/me")
    public String me(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello user: %s!", jwt.getClaimAsString("preferred_username"));
    }

    @GetMapping("/api/admin")
    public String admin(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello admin: %s!", jwt.getClaimAsString("preferred_username"));
    }

}
