package com.zerobase.restaurant.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.restaurant.dto.ResponseDto;
import com.zerobase.restaurant.enums.CustomError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        List<String> nonAuthUrls = Arrays.asList("/restaurants", "/restaurant/*","/reservation"); //해당 url에 대해서 jwt 인증 스킵
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //get method이면서, 리스트에 포함된 경로일 경우
        boolean isGetPublic = "GET".equals(method) && nonAuthUrls.stream().anyMatch(url -> pathMatcher.match(url, requestURI));
        //signUp과 signIn
        boolean isPostSigninOrSignup = "POST".equals(method) && ("/signin".equals(requestURI) || "/signup".equals(requestURI));

        boolean isSkip = isGetPublic || isPostSigninOrSignup;
        if(isSkip){//목록에 존재하는 url이면 스킵
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String token = parseBearerToken(request);//token 추출
            User user = parseUserSpecification(token);//유저 정보 추출
            AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());//해당 값 전달
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);//다음 filter로 이동
        }catch (ExpiredJwtException e){
            setErrorResponse(response, CustomError.JWT_EXPIRED);
        }catch (JwtException e){
            setErrorResponse(response, CustomError.HEADER_WITHOUT_TOKEN);
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.toLowerCase().startsWith("bearer ")) {
            throw new JwtException("Missing or malformed Authorization header");
        }
        return token.substring(7);
    }
    private User parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 10)
                .map(jwtTokenProvider::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void setErrorResponse(HttpServletResponse response, CustomError customError) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        log.error(customError.name(), customError.getErrorMessage());
        ResponseDto<?> responseDto = ResponseDto.error(HttpStatus.UNAUTHORIZED, customError);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        } catch (IOException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

}
