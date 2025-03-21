package com.zerobase.restaurant.repository;

import com.zerobase.restaurant.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, UUID> {
}
