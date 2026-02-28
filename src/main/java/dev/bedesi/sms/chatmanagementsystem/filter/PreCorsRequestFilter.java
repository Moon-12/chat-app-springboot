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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        chain.doFilter(request, response);
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
