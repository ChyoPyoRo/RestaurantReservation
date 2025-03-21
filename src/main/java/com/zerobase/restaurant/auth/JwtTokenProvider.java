package com.zerobase.restaurant.auth;

import com.zerobase.restaurant.entity.UserData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretkey}")
    private String secretKey;//이전 커밋하고 다른 값
    private Long expirationMs = 3600*1000L;//1시간
    private SecretKey key;
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));//Base64로 encode된 String 을 기반으로 Key 생성
    }

    public String createToken(UserData user) {
        Date now = new Date();
        String userUuid = user.getUuid().toString();//uuid 값
        String role = user.getRole().getRole();//getRole 값
        return Jwts.builder()
                .subject(userUuid+":"+role)//jwt 제목
                .issuedAt(now)//생성 시간
                .expiration(new Date(now.getTime() + expirationMs))//기한
                .issuer("finance")//jwt 발급자
                .signWith(key)//암호화 키
                .compact();
    }


    public String validateTokenAndGetSubject(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();//제목 돌려주기
    }

}
