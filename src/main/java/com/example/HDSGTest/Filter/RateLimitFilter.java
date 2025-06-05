package com.example.HDSGTest.Filter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Value("${ratelimit.requests:10}")
    private int requests;
    @Value("${ratelimit.duration:1}")
    private int durationMinutes;

    private Bucket newBucket() {
        Refill refill = Refill.greedy(requests, java.time.Duration.ofMinutes(durationMinutes));
        Bandwidth limit = Bandwidth.classic(requests, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private String getClientIdentifier(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Sử dụng toàn bộ JWT làm key
        }
        return request.getRemoteAddr();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // Bỏ qua rate limit cho swagger và openapi
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        String clientId = getClientIdentifier(httpRequest);
        Bucket bucket = cache.computeIfAbsent(clientId, k -> newBucket());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response); // Cho qua
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.getWriter().write("Too many requests - try again later");
        }
    }
}
