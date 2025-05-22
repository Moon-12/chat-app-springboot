package dev.bedesi.sms.chatmanagementsystem.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class PreCorsRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Example: Logging or checking headers
        System.out.println("Incoming request to: " + httpRequest.getRequestURI());

        // You can also reject or modify the request before it reaches Spring
        // e.g., check a custom token, log, etc.

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
