package dev.bedesi.sms.chatmanagementsystem.filter;

import dev.bedesi.sms.chatmanagementsystem.dto.VerifyTokenResponseDTO;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final WebClient webClient;

    @Value("${sso.url}")
    private String ssoUrl;


    public JwtRequestFilter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("inside jwt request filter");

        String authHeader = request.getHeader("Authorization");
        String appKey = request.getHeader("appKey");
        logger.info("authHeader: {}", authHeader);

        // Handle missing or malformed Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("No Authorization header present, skipping JWT validation");
            filterChain.doFilter(request, response); // just continue the filter chain
            return; // stop further processing in this filter
        }
        String token = authHeader.substring(7);
        logger.info("token: {}", token);

        logger.info("sso url {}",ssoUrl);
        try {
            VerifyTokenResponseDTO userInfo =webClient.post()
                    .uri(ssoUrl)
                    .header("Authorization", "Bearer " + token)
                    .header("appKey", appKey)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            clientResponse -> Mono.error(new RuntimeException("Token verification failed"))
                    )
                    .bodyToMono(VerifyTokenResponseDTO.class)
                    .block(); // block only in blocking context


            logger.info("userInfo from verify-token: {}", userInfo);

            List<GrantedAuthority> authorities = userInfo.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userInfo, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception ex) {
            logger.error("Authentication error: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


    logger.info("filter crossed");
    filterChain.doFilter(request, response);
    }
}



