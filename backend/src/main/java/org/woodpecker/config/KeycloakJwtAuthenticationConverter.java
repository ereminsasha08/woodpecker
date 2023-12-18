package org.woodpecker.config;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(source, Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source)
                        .stream(), extractResourceRoles(source).stream())
                .collect(toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        HashMap<Object, LinkedTreeMap<Object, ArrayList<Object>>> resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        LinkedTreeMap<Object, ArrayList<Object>> woodpecker = resourceAccess.get("woodpecker");
        ArrayList<Object> roles = woodpecker.get("roles");
        return roles.isEmpty() ? emptySet() : roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(toSet());
    }
}