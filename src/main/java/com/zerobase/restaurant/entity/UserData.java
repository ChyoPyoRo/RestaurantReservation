package com.zerobase.restaurant.entity;

import com.zerobase.restaurant.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    //로그인 할 때 id값 대신 휴대폰 번호 사용
    @Column(name="phone_number", nullable=false, unique=true)
    private String phoneNumber;
    //암호화 해서 저장
    @Column
    private String password;
    //Enum값으로 저장
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
