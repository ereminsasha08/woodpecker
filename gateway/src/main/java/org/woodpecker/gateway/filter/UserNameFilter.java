package org.woodpecker.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.Objects;

@Order
@Slf4j
@Component
@RequiredArgsConstructor
public class UserNameFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        System.out.println("The authentication name from the token is : " + getUsername(requestHeaders));
        return chain.filter(exchange);
    }


    private String getUsername(HttpHeaders requestHeaders) {
        String username = "";
        if (FilterUtils.getAuthToken(requestHeaders) != null) {
            String authToken = Objects.requireNonNull(FilterUtils.getAuthToken(requestHeaders)).replace("Bearer ", "");
            JSONObject jsonObj = decodeJWT(authToken);
            try {
                username = Objects.requireNonNull(jsonObj).getString("preferred_username");
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        return username;
    }


    private JSONObject decodeJWT(String JWTToken) {
        String[] split_string = JWTToken.split("\\.");
        if (split_string.length <= 1)
            return null;
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        return new JSONObject(body);
    }

}