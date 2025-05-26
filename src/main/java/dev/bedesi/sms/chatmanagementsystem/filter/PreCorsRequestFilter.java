package dev.bedesi.sms.chatmanagementsystem.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
@WebFilter("/*")
public class PreCorsRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(PreCorsRequestFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        // Example: Logging or checking headers
        String requestURI = httpRequest.getRequestURI();
        System.out.println("Incoming request to: " + requestURI);

        System.out.println("Request Headers:");
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = httpRequest.getHeader(headerName);
                System.out.println(headerName + ": " + headerValue);
            }
        }
        // Skip token validation for WebSocket and SockJS-related requests
        if(requestURI.startsWith("/chat-app-api/activeMessageListener")){
            chain.doFilter(request, response);
            return;
        }
        System.out.println(httpRequest.getHeader("Referer"));
        // You can also reject or modify the request before it reaches Spring
        // e.g., check a custom token, log, etc.


        // Check for x-auth-token header

        String authToken = httpRequest.getHeader("x-auth-token");
        if (authToken == null || authToken.isEmpty()) {
            logger.warn("Missing x-auth-token header for request to: {}", httpRequest.getRequestURI());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "x-auth-token header is missing");
            return; // Stop further processing
        }

        // Validate the x-auth-token
        if (!isValidHeader(authToken)) {
            logger.warn("Invalid x-auth-token for request to: {}", httpRequest.getRequestURI());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "x-auth-token is invalid");
            return; // Stop further processing
        }
        // Proceed with the request
        chain.doFilter(request, response);
    }
    private boolean isValidHeader(String headerValue) {
        // Add your validation logic here (e.g., check for specific values)
        return headerValue != null && !headerValue.isEmpty() && headerValue.equals(System.getenv("APP_PASSWORD"));
    }
}
