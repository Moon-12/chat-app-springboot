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
        // Log request metadata
        logRequestMetadata(httpRequest);
        // Example: Logging or checking headers
        String requestURI = httpRequest.getRequestURI();
        logger.info("Incoming request to: " + requestURI);

        logger.info("Request Headers:");
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = httpRequest.getHeader(headerName);
                logger.info(headerName + ": " + headerValue);
            }
        }
        // Skip token validation for WebSocket and SockJS-related requests
        if(requestURI.startsWith("/chat-app-api/activeMessageListener")){
            chain.doFilter(request, response);
            return;
        }
        logger.info(httpRequest.getHeader("Referer"));
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
        return headerValue != null && !headerValue.isEmpty() && headerValue.equals(System.getenv("SERVER_KEY"));
    }

    private void logRequestMetadata(HttpServletRequest request) {
        // Log basic request details
        logger.info("=== Incoming Request ===");
        logger.info("Method: {}", request.getMethod());
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Query String: {}", request.getQueryString());
        logger.info("Protocol: {}", request.getProtocol());
        logger.info("Remote Address: {}", request.getRemoteAddr());
        logger.info("Remote Host: {}", request.getRemoteHost());
        logger.info("Remote Port: {}", request.getRemotePort());
        logger.info("Scheme: {}", request.getScheme());
        logger.info("Server Name: {}", request.getServerName());
        logger.info("Server Port: {}", request.getServerPort());
        logger.info("Referer: {}", request.getHeader("Referer"));
        logger.info("User-Agent: {}", request.getHeader("User-Agent"));

        // Log all headers
        logger.info("Request Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                logger.info("{}: {}", headerName, headerValue);
            }
        }
        logger.info("=====================");
    }
}
