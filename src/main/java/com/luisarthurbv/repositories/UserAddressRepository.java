package com.luisarthurbv.repositories;

import com.luisarthurbv.entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {

    UserAddressEntity findByUserId(long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_address where user_id = ?1", nativeQuery = true)
    void deleteByUserId(long userId);

}
