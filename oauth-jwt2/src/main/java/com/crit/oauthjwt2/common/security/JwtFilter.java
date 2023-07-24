package com.crit.oauthjwt2.common.security;

import com.crit.oauthjwt2.service.UserService;
import com.crit.oauthjwt2.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String key;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            if (request.getServletPath().equals("/members/oauth/token")) {
                filterChain.doFilter(request, response);
            } else {

                request.setAttribute("Authorization", "Token이 없습니다.");
                String errorCode = "Token이 없습니다.";
                final Map<String, Object> body = new HashMap<>();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                body.put("status", 402);
                body.put("error", "Empty");
                body.put("message", errorCode);
                body.put("path", request.getServletPath());
                final ObjectMapper mapper = new ObjectMapper();

                mapper.writeValue(response.getOutputStream(), body);
                response.setStatus(HttpServletResponse.SC_OK);

            }
        } else {
            String token = authorization.split(" ")[1];
            String Id = "";
            try {

                if (JwtUtil.isExpired(token, key)) {

                } else {

                    Id = JwtUtil.getMemberId(token, key);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            Id, null, List.of(new SimpleGrantedAuthority("USER")));
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                request.setAttribute("Authorization", "토큰이 만료되었습니다.");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("Authorization", "유효하지 않은 토큰입니다.");

            }
            filterChain.doFilter(request, response);
        }
    }
}
