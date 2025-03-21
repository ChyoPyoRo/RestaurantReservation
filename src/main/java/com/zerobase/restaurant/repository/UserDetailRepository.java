package com.zerobase.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.restaurant.dto.SignInRequestDto;
import com.zerobase.restaurant.entity.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.zerobase.restaurant.entity.QUserData.userData;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDetailRepository {
    private final UserDataRepository userDataRepository;
    private final JPAQueryFactory queryFactory;

    public void save(UserData userData) {
        userDataRepository.save(userData);//저장은 JPA로 진행
    }

    public boolean alreadyUsedPhoneNumber(String phoneNumber) {
        //존재하면 true, 없으면 false
        return queryFactory.selectFrom(userData).where(userData.phoneNumber.eq(phoneNumber)).fetchOne() != null;
    }

    public UserData findUserByPhoneNumber(SignInRequestDto requestDto) {
        return queryFactory.selectFrom(userData)
                .where(userData.phoneNumber.eq(requestDto.getPhoneNumber()))
                .fetchOne();
    }
}
