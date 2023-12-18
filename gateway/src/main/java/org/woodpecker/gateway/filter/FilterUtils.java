package org.woodpecker.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FilterUtils {
    public static final String AUTH_TOKEN = "Authorization";

    public static String getAuthToken(HttpHeaders requestHeaders) {
        List<String> header = requestHeaders.get(AUTH_TOKEN);
        if (header != null && !header.isEmpty()) {
            return header.get(0);
        } else {
            return null;
        }
    }
}